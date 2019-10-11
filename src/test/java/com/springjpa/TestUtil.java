package com.springjpa;

import com.springjpa.dto.CartDto;
import com.springjpa.dto.CustomerCartDto;
import com.springjpa.dto.CustomerDto;
import com.springjpa.model.Customer;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class TestUtil {



    public static CustomerDto setCustomerData() {
        int i=16;
        Long l = new Long(i);

        CustomerDto custDto = new CustomerDto();
        custDto.setId(l);
        custDto.setFirstname("Matt");
        custDto.setLastname("Anderson");
        custDto.setHometown("Blaine");
        return custDto;
    }

    public static Customer setCustData() {
        int i=16;
        Long l = new Long(i);

        Customer cust = new Customer();
        cust.setid(l);
        cust.setFirstName("Matt");
        cust.setLastName("Anderson");
        cust.setHomeTown("Blaine");
        return cust;
    }

    public static CartDto setCartData() {
        int id=1;
        Long lid = new Long(id);

        int custid=5;
        Long lcustid = new Long(custid);

        int cartid=1;
        Long lcartid = new Long(cartid);

        Double retail=19.99;
        BigDecimal dretail = new BigDecimal(retail);

        CartDto cartDto = new CartDto();
        cartDto.setId(lid);
        cartDto.setCustid(lcustid);
        cartDto.setCartnumber(lcartid);
        cartDto.setItem("BigToy");
        cartDto.setItemDescription("BigBlueToy");
        cartDto.setRetail(dretail);
        return cartDto;
    }


    public static CustomerCartDto setCartCustData() {
        int id=1;
        Long lid = new Long(id);

        CustomerCartDto custcartDto = new CustomerCartDto();
        custcartDto.setId(lid);
        custcartDto.setFirstname("Matt");
        custcartDto.setLastname("Anderson");
        custcartDto.setCarts(getCartInfo());  //create a list of the cartDTO to return


        return custcartDto;
    }

    //method that converts the cartDTO to a list
    public static List<CartDto> getCartInfo() {

        int id=1;
        Long lid = new Long(id);

        int custid=5;
        Long lcustid = new Long(custid);

        int cartid=1;
        Long lcartid = new Long(cartid);

        Double retail=1.00;
        BigDecimal dretail = new BigDecimal(retail);

        int cnum=1;
        Long cnumb = new Long(cnum);

        List<CartDto> cartList = new ArrayList<>();

        CartDto cartDto = new CartDto();
        cartDto.setId(lid);
        cartDto.setCustid(lcustid);
        cartDto.setItem("BigToy");
        cartDto.setItemDescription("BigBlueToy");
        cartDto.setRetail(dretail);
        cartDto.setCartnumber(cnumb);


        //Add the cartDTO to the list
        cartList.add(cartDto);

        return cartList;
    }

}
