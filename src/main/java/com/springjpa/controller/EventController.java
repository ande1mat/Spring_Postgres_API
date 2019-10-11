package com.springjpa.controller;

/**
 * Created by z042183 on 6/10/18.
 */

import com.springjpa.dto.CustomerCartDto;
import com.springjpa.dto.CustomerDto;
import com.springjpa.dto.CartDto;
import com.springjpa.model.Customer;
import com.springjpa.model.Cart;
import com.springjpa.repository.CartRepository;
import com.springjpa.repository.CustomerRepository;
import com.springjpa.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;



@RestController
public class EventController {

    private CustomerService service;
    private CustomerDto customerDto;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    //---Logback Config Options --->> https://logback.qos.ch/manual/configuration.html
    //private static final Logger logger = LoggerFactory.getLogger(EventController.class);


    @Autowired
    public EventController(CustomerService service, CustomerRepository customerRepository, CartRepository cartRepository) {
        this.service = service;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    //http://localhost:8090/findCust?id=5
    //GET End point that returns  Customer details
    @RequestMapping(value="/findCust", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<CustomerDto> getCust(@RequestParam(value="id") @NotBlank Long id) {

        CustomerDto cust = service.getCustById(id);
        //logger.debug("get /findCust called");

        return new ResponseEntity<>(cust, HttpStatus.OK);
    }

    //http://localhost:8090/findCart?id=5
    //GET End point that returns Cart details only
    @RequestMapping(value="/findCart", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<CartDto> getCartinfo(@RequestParam("id") @NotBlank Long id, @RequestParam("custid") @NotBlank Long custid) {

        CartDto cartinfo = service.getCartByCustId(id, custid);
        //logger.debug("get /findCust called");
        return new ResponseEntity<>(cartinfo, HttpStatus.OK);
    }


    //http://localhost:8090/findCustCart?id=5
    //GET End point that returns both Customer & Cart details
    @RequestMapping(value="/findCustCart", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<CustomerCartDto> getCustCart(@RequestParam("id") @NotBlank Long id) {
        CustomerCartDto cust = service.getCustCartById(id);
        //logger.debug("get /findCustCart called");
        return new ResponseEntity<>(cust, HttpStatus.OK);
    }

    //Add POST endpoint for inserting new Customer
    //http://localhost:8090/newcustomer
    /*
        {
            "firstName": "Lucky",
            "lastName": "Seventeen",
            "homeTown": "Dallas"
        }
    */
    @RequestMapping(value = "/newcustomer", method = RequestMethod.POST)
    public ResponseEntity saveCustomer(@RequestBody Customer newCustomer){
        service.saveCustomer(newCustomer);
        //logger.debug("get /newcustomer called");
        return new ResponseEntity(HttpStatus.OK);
    }


    //Add POST endpoint for inserting new Cart for Existing Customer
    //http://localhost:8090/newcustomer
    /*
        {
            "custid": 16,
            "item": "Pezz",
            "itemDescription": "Pezz Bulldozer 33",
            "retail": 1.50,
            "cartnumber": 3
        }
    */
    @RequestMapping(value = "/newcart", method = RequestMethod.POST)
    public ResponseEntity saveCart(@RequestBody Cart newCart){
        service.saveCart(newCart);
        //logger.debug("get /newcart called");
        return new ResponseEntity(HttpStatus.OK);
    }

    //Add Post endpoint for updating existing Customer
    /*
        {
            "Id" : 16,
            "Firstname": "Lucky",
            "Lastname": "Sixteen",
            "Hometown": "Iowa City"
        }
    */
    @RequestMapping(value = "/updatecustomer", method = RequestMethod.POST)
    public ResponseEntity updateCustomer(@RequestBody CustomerDto updateCustomer){
        service.updateCustomer(updateCustomer.getId(), updateCustomer.getFirstname(), updateCustomer.getLastname(), updateCustomer.getHometown());
        //logger.debug("get /updatecustomer called");
        return new ResponseEntity(HttpStatus.OK);
    }

    //Add Put endpoint for updating existing Customer's Cart information
    /*
        {
            "Id": 11,
            "CustId": 16,
            "Item": "Lucky Test",
            "Item Description": "Very Lucky Toy",
            "Retail": 133.10,
            "Cart Number": 3
        }
    */
    @RequestMapping(value = "/updatecart", method = RequestMethod.POST)
    public ResponseEntity updateCart(@RequestBody CartDto updateCart){
        service.updateCart(updateCart.getId(), updateCart.getItem(), updateCart.getItemDescription(), updateCart.getRetail(), updateCart.getCartnumber());

        //logger.debug("get /updatecart called");
        return new ResponseEntity(HttpStatus.OK);
    }


    //Add Delete endpoint for deleting Customer and all Cart info (Cascade...)
    /*
    {
        "Id": 4
    }
    You need to delete Customers without Cart details, else first delete the Cart.

     */
    @RequestMapping(value = "/deletecustomer", method = RequestMethod.POST)
    public ResponseEntity deleteCustomer(@RequestBody CustomerDto deleteCustomer){
        service.deleteCustomer(deleteCustomer.getId());
        //logger.debug("get /deletecustomer called");
        return new ResponseEntity(HttpStatus.OK);
    }

    //Add Delete endpoint for deleting Customer and all Cart info (Cascade...)
    /*
    {
        "Id": 8,
        "custId": 8
    }

    You need to delete Carts with the given Id and Customer Id

     */
    @RequestMapping(value = "/deletecart", method = RequestMethod.POST)
    public ResponseEntity deleteCustomer(@RequestBody CartDto deleteCart){
        service.deleteCart(deleteCart.getId());
        //logger.debug("get /deletecart called");
        return new ResponseEntity(HttpStatus.OK);
    }




}

