package com.springjpa.service;


import com.springjpa.mapper.ApiDTOBuilder;
import com.springjpa.mapper.CustToCartMapper;
import com.springjpa.dto.*;
import com.springjpa.model.Cart;
import com.springjpa.model.Customer;
import com.springjpa.model.Weather;
import com.springjpa.repository.CartRepository;
import com.springjpa.repository.CustomerRepository;
import com.springjpa.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.lang.String;
import java.math.BigDecimal;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


/**
 * Created by z042183 on 10/14/18.
 * If the data needs any transformation, we do it in the service layer before send it to the DAO.
 * The DAO returns a entity or persistence object
 */
@Component
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WeatherRepository weatherRepository;


    public CustomerService(CustomerRepository customerRepository, CartRepository cartRepository, WeatherRepository weatherRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.weatherRepository = weatherRepository;
    }

    @HystrixCommand (fallbackMethod = "defaultWeather")
    public CustomerDto getCustById(Long id) {
        //Call the CartDAO repository
        Customer cust = customerRepository.findByid(id);

        //If the Customer's hometown is present get the current weather conditions
        if (cust.getHomeTown() != null)
        { return ApiDTOBuilder.custToCustWeatherDTO(cust, getWeather(cust.getHomeTown())); }
        else
        { return ApiDTOBuilder.custToCustDTO(cust); }
    }

    //Hystrix Fallback Method when Weather API Fails.
    public CustomerDto defaultWeather(Long id) {

        Customer cust = customerRepository.findByid(id);
        System.out.println("Hystrix circuit breaker triggered, using default method");
        return ApiDTOBuilder.custToCustWeather(cust);
    }

    public CartDto getCartByCustId(Long id, Long custid) {
        //Call the CartDAO repository
        Cart cart = cartRepository.findByIdAndCustid(id, custid);

        return ApiDTOBuilder.custToCartDTO(cart);
    }

    public CustomerCartDto getCustCartById(Long id) {
        //Call the CarDAO repository
        Customer cust = customerRepository.findByid(id);

        //Return the Mapstruct mapper interface object (CustomerCartDto) from the service call
        return CustToCartMapper.INSTANCE.custToCustomerCartDto(cust);
    }

    public Customer saveCustomer (Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    public Cart saveCart (Cart cart) {
        cartRepository.save(cart);
        return cart;
    }

    public void deleteCustomer (Long id) {
        Customer cust = customerRepository.findByid(id);
        customerRepository.delete(cust);
    }

    public void deleteCart (Long id) {
        Cart cart = cartRepository.findByid(id);
        cartRepository.delete(cart);
    }


    public Cart updateCart (Long id, String item, String itemdesc, BigDecimal retail, Long cartnumber) {
        Cart cart = cartRepository.findByid(id);
        cart.setItem(item);
        cart.setItemDescription(itemdesc);
        cart.setRetail(retail);
        cart.setCartnumber(cartnumber);
        cartRepository.save(cart);
        return cart;

    }

    public Customer updateCustomer (Long id, String fname, String lname, String htown) {
        Customer cust = customerRepository.findByid(id);

        cust.setFirstName(fname);
        cust.setLastName(lname);
        cust.setHomeTown(htown);
        customerRepository.save(cust);
        return cust;

    }

    //Call the external Weather API or the Redis Cache for the weather data
    public WeatherResultDto getWeather(String hometown)
    {
        WeatherResultDto weatherResultDto = new WeatherResultDto();

        //Check if Redis is up or not, if not up call the API and skip Redis cache else check cache for data
        JedisPool pool = new JedisPool();
        try {
            Jedis jedis = pool.getResource();
            // Is connected
            System.out.println("Redis is online, checking its data");

            //search the Redis cache for the hometown weather
            //'If false, Cache does not contain the hometown weather, then call the API, and also save the hometown/weather to the cache
            if (!findCachedWeather(hometown))
            {
                //if not in the Cache call the API and save the weather in the cache
                //Find weather in Redis and and set the weatherResultDto with the contents from Redis
                weatherResultDto = findWeather (hometown);
                System.out.println("Using the API for the hometown weather");

                //Save to Redis Cache
                saveCachedWeather(hometown, weatherResultDto);
                System.out.println("saved the hometown weather to the Redis cache");
            } else {
                //if true found it in the cache return the cache contents
                //Find weather in Redis and and set the weatherResultDto with the contents from Redis
                weatherResultDto = findCachedValuesWeather (hometown);
                System.out.println("Found the hometown weather in the Redis cache, using its data");
            }
        } catch (JedisConnectionException e) {
            // Not connected
            System.out.println("Redis is offline, skipping its use");
            //Call the external Weather API
            weatherResultDto = findWeather(hometown);
        }
        return weatherResultDto;
    }

    private void saveCachedWeather(String hometown, WeatherResultDto weatherResultDto) {
        Weather weather = new Weather();
        weather.setId(hometown);
        weather.setWeather(weatherResultDto.getWeather());
        weather.setMain(weatherResultDto.getMain());
        weatherRepository.save(weather);
    }

    public boolean findCachedWeather(String hometown) {
        //Check if we have a matched hometown in Redis
        if (weatherRepository.existsById(hometown)) {
            System.out.println("Redis found the hometown weather");
            return true;
        }
        //Cache config is set to expire the key after 5 minutes, see Weather Model Entity Object settings
        System.out.println("Redis did not find the hometown weather");
        return false;
    }

    private WeatherResultDto findWeather(String hometown) {
        WeatherResultDto weatherResultDto = new WeatherResultDto();
        System.out.println("Redis did not find the hometown weather, calling the external API");

        try {
            //Call the Weather  API based on the Customers Home City Name
            final String uri = "http://api.openweathermap.org/data/2.5/weather?q=" + hometown + "&units=imperial&APPID=d9687f6bd4c0adb550b79bbaddd3e421";
            RestTemplate restTemplate = new RestTemplate();
            weatherResultDto = restTemplate.getForObject(uri, WeatherResultDto.class);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Encountered a problem calling the Weather API");
        }
        return weatherResultDto;
    }

    private WeatherResultDto findCachedValuesWeather(String hometown) {
        WeatherResultDto weatherResultDto = new WeatherResultDto();
        Weather retrievedWeather2 = weatherRepository.findById(hometown).get();
        weatherResultDto.setWeather(retrievedWeather2.getWeather());
        weatherResultDto.setMain(retrievedWeather2.getMain());

        return weatherResultDto;
    }

}
