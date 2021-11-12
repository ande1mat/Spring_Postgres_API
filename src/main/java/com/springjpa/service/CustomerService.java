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

    public CustomerDto getCustById(Long id) {
        //Call the CartDAO repository
        Customer cust = customerRepository.findByid(id);

        //If the Customer's hometown is present get the current weather conditions
        if (cust.getHomeTown() != null)
        { return ApiDTOBuilder.custToCustWeatherDTO(cust, getWeather(cust.getHomeTown())); }
        else
        { return ApiDTOBuilder.custToCustDTO(cust); }

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


    public WeatherResultDto getWeather(String hometown)
    {

        /* ORIGINAL CALL JUST TO API
        WeatherResultDto weatherResultDto = new WeatherResultDto();

        //Call the Weather  API based on the Customers Home City Name
        final String uri = "http://api.openweathermap.org/data/2.5/weather?q=" + hometown + "&units=imperial&APPID=d9687f6bd4c0adb550b79bbaddd3e421";
        RestTemplate restTemplate = new RestTemplate();

        weatherResultDto = restTemplate.getForObject(uri, WeatherResultDto.class);
        */

        WeatherResultDto weatherResultDto = new WeatherResultDto();

        //Check if Redis is up or not, if not up call the API and skip Redis cache else check cache for data
        JedisPool pool = new JedisPool();
        try {
            Jedis jedis = pool.getResource();
            // Is connected
            System.out.println("Redis is online, checking its data");


            //search the Redis cache for the hometown weather
            //'If False Cache does not contain the hometown weather, then call the API, and also save the hometown/weather to the cache

            //ONLY ISSUE IS WE NEED TO CHANGE THIS TO RETURN weatherResultDto Else the weather and main will be blank and not set below
            if (!findCachedWeather(hometown))

            {
                weatherResultDto = findWeather(hometown);
                Weather weather = new Weather();
                //WeatherResultDto weatherResultDto = new WeatherResultDto ();

                weather.setId(hometown);
                weather.setWeather(weatherResultDto.getWeather());
                weather.setMain(weatherResultDto.getMain());

                //Save to Redis Cache
                weatherRepository.save(weather);
                System.out.println("saved the hometown weather to the Redis cache");
            }

        } catch (JedisConnectionException e) {
            // Not connected
            System.out.println("Redis is offline, skipping its use");
            //Call the external Weather API
            weatherResultDto = findWeather(hometown);
        }

        return weatherResultDto;

    }

    private void saveCachedWeather(String hometown) {
        Weather weather = new Weather();
        WeatherResultDto weatherResultDto = new WeatherResultDto ();

        weather.setId(hometown);
        weather.setWeather(weatherResultDto.getWeather());

        weather.setMain(weatherResultDto.getMain());

        weatherRepository.save(weather);
        System.out.println("saved the hometown weather to the Redis cache");

    }

    private boolean findCachedWeather(String hometown) {
        WeatherResultDto weatherResultDto = new WeatherResultDto();

        //#############CODE IS FAILING RIGHT HERE!!!!#########//
        //https://codeflex.co/java-optional-no-more-nullpointerexception/
        //Might need to catch that the Entity returned is Null, in this case in Redis.
        //LOOK at this next for my CRUD Repository
        // https://stackoverflow.com/questions/51058898/spring-data-jpa-findbyid-method-returning-null-instead-of-empty-optional
        //Weather retrievedWeather = weatherRepository.findById(hometown).get();
        //Weather retrievedWeather = weatherRepository.findById(hometown);

        //Check if we have a matched hometown in Redis
        //if (retrievedWeather.getId() != null) {
        if (weatherRepository.existsById(hometown)) {
            Weather retrievedWeather2 = weatherRepository.findById(hometown).get();
            weatherResultDto.setWeather(retrievedWeather2.getWeather());
            weatherResultDto.setMain(retrievedWeather2.getMain());

            System.out.println("Redis found the hometown weather");
            return true;

        }
        System.out.println("Redis did not find the hometown weather");

        return false;
    }


    private WeatherResultDto findWeather(String hometown) {
        WeatherResultDto weatherResultDto = new WeatherResultDto();

        System.out.println("Redis did not find the hometown weather, calling the external API");

        //Call the Weather  API based on the Customers Home City Name
        final String uri = "http://api.openweathermap.org/data/2.5/weather?q=" + hometown + "&units=imperial&APPID=d9687f6bd4c0adb550b79bbaddd3e421";
        RestTemplate restTemplate = new RestTemplate();

        weatherResultDto = restTemplate.getForObject(uri, WeatherResultDto.class);

        return weatherResultDto;
    }

}
