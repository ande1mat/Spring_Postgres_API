package com.springjpa.mapper;

import com.springjpa.dto.CustomerDto;
import com.springjpa.dto.CartDto;
import com.springjpa.dto.WeatherResultDto;
import com.springjpa.model.Customer;
import com.springjpa.model.Cart;


/**
 * Created by z042183 on 10/14/18.
 */
public class ApiDTOBuilder {
    public static CustomerDto custToCustDTO(Customer cust) {
        CustomerDto CustomerDto = new CustomerDto();

        CustomerDto.setId(cust.getid());
        CustomerDto.setFirstname(cust.getFirstName());
        CustomerDto.setLastname(cust.getLastName());
        CustomerDto.setHometown(cust.getHomeTown());

        return CustomerDto;
    }

    public static CustomerDto custToCustWeatherDTO(Customer cust, WeatherResultDto weatherResultDto) {
        CustomerDto CustomerDto = new CustomerDto();

        CustomerDto.setId(cust.getid());
        CustomerDto.setFirstname(cust.getFirstName());
        CustomerDto.setLastname(cust.getLastName());
        CustomerDto.setHometown(cust.getHomeTown());

        CustomerDto.setWeather(weatherResultDto.getWeather());
        CustomerDto.setMain(weatherResultDto.getMain());

        return CustomerDto;
    }

    public static CartDto custToCartDTO(Cart cart) {
        CartDto CartDto = new CartDto();
        CartDto.setId(cart.getId());
        CartDto.setCustid(cart.getCustid());
        CartDto.setItem(cart.getItem());
        CartDto.setItemDescription(cart.getItemDescription());
        CartDto.setRetail(cart.getRetail());
        CartDto.setCartnumber(cart.getCartnumber());

        return CartDto;
    }


}
