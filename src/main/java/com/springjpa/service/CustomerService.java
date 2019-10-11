package com.springjpa.service;

import com.springjpa.mapper.ApiDTOBuilder;
import com.springjpa.mapper.CustToCartMapper;
import com.springjpa.dto.*;
import com.springjpa.model.Cart;
import com.springjpa.model.Customer;
import com.springjpa.repository.CartRepository;
import com.springjpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.lang.String;
import java.math.BigDecimal;


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

    public CustomerService(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
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
        WeatherResultDto weatherResultDto = new WeatherResultDto();

        //Call the Weather  API based on the Customers Home City Name
        final String uri = "http://api.openweathermap.org/data/2.5/weather?q=" + hometown + "&units=imperial&APPID=d9687f6bd4c0adb550b79bbaddd3e421";
        RestTemplate restTemplate = new RestTemplate();

        weatherResultDto = restTemplate.getForObject(uri, WeatherResultDto.class);

        return weatherResultDto;
    }

}
