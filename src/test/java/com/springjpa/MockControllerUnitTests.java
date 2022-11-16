package com.springjpa;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.springjpa.controller.EventController;
import com.springjpa.dto.CustomerCartDto;
import com.springjpa.model.Customer;
import com.springjpa.model.Cart;
import com.springjpa.repository.CartRepository;
import com.springjpa.repository.CustomerRepository;
import com.springjpa.dto.CustomerDto;
import com.springjpa.dto.CartDto;
import com.springjpa.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.http.HttpStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {EventController.class})
@WebMvcTest(EventController.class)
public class MockControllerUnitTests {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CartRepository cartRepository;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);
    }


    //Get Customer Endpoint Mock Test
    @Test
    public void getCustomerTest() throws Exception {
        CustomerDto custDtoList = TestUtil.setCustomerData();

        //Here we tell the Service to set the DTO object with what we want in the DTO
        when(customerService.getCustById(anyLong())).thenReturn(custDtoList);

        //Here we Mock the API Get call and check the results
        mockMvc.perform(MockMvcRequestBuilders.get("/findCust?id=16"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Firstname").value("Matt"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Lastname").value("Anderson"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Hometown").value("Blaine"));

    }


    //Get Cart Endpoint Mock Test
    @Test
    public void getCartTest() throws Exception {
        CartDto cartDtoList = TestUtil.setCartData();

        //Here we tell the Service to set the DTO object with what we want in the DTO
        when(customerService.getCartByCustId(anyLong(), anyLong())).thenReturn(cartDtoList);

        //Here we Mock the API Get call and check the results
        mockMvc.perform(MockMvcRequestBuilders.get("/findCart?id=1&custid=5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.custId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Item").value("BigToy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.['Item Description']").value("BigBlueToy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Retail").value(19.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$.['Cart Number']").value(1));

    }

    //Get Customer and Cart Details Endpoint Mock Test
    @Test
    public void getCustCartTest() throws Exception {
        CustomerCartDto customerCartDto = TestUtil.setCartCustData();

        //Here we tell the Service to set the DTO object with what we want in the DTO
        when(customerService.getCustCartById(anyLong())).thenReturn(customerCartDto);

        //Here we Mock the API Get call and check the results
        mockMvc.perform(MockMvcRequestBuilders.get("/findCustCart?id=5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Firstname").value("Matt"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Lastname").value("Anderson"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carts[?(@.Id == '" + 1 + "')]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carts[?(@.custId == '" + 5 + "')]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carts[?(@.Item == 'BigToy')]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carts[?(@.['Item Description'] == 'BigBlueToy')]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carts[?(@.Retail == '" + 1 + "')]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carts[?(@.['Cart Number'] == '" + 1 + "')]").exists());

    }


    @Test
    public void createCustomerTest() throws Exception {

        CustomerDto cust = TestUtil.setCustomerData();

        // customerService.saveCart to respond back with mock Cart
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(new Customer());


        // Send cart as body to /newcustomer
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/newcustomer")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Matt\",\"lastName\": \"Anderson\",\"homeTown\": \"Blaine\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

    @Test
    public void updateCustomerTest() throws Exception {

        CustomerDto cust = TestUtil.setCustomerData();

        // customerService.saveCart to respond back with mock Cart
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(new Customer());


        // Send cart as body to /updatecustomer
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/updatecustomer")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Matt\",\"lastName\": \"Anderson\",\"homeTown\": \"Blaine\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

    @Test
    public void deleteCustomerTest() throws Exception {

        CustomerDto cust = TestUtil.setCustomerData();

        // customerService.saveCart to respond back with mock Cart
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(new Customer());


        // Send cart as body to /deletecustomer
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/deletecustomer")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"Id\": \"5\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }



    @Test
    public void createCartTest() throws Exception {

        CartDto cart = TestUtil.setCartData();

        // customerService.saveCart to respond back with mock Cart
        when(customerService.saveCart(any(Cart.class))).thenReturn(new Cart());


        // Send cart as body to /newcart
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/newcart")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"custid\": \"5\",\"item\": \"BigToy\",\"itemDescription\": \"BigBlueToy\",\"retail\": \"19.99\",\"cartnumber\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

    @Test
    public void updateCartTest() throws Exception {

        CartDto cart = TestUtil.setCartData();

        // customerService.saveCart to respond back with mock Cart
        when(customerService.saveCart(any(Cart.class))).thenReturn(new Cart());


        // Send cart as body to /updatecart
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/updatecart")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"custid\": \"5\",\"item\": \"BigToy\",\"itemDescription\": \"BigBlueToy\",\"retail\": \"19.99\",\"cartnumber\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

    @Test
    public void deleteCartTest() throws Exception {

        CartDto cart = TestUtil.setCartData();

        // customerService.saveCart to respond back with mock Cart
        when(customerService.saveCart(any(Cart.class))).thenReturn(new Cart());


        // Send cart as body to /deletecart
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/deletecart")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"Id\": \"1\",\"custid\": \"5\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

}