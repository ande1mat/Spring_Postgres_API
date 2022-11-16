package com.springjpa;

import com.springjpa.controller.EventController;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {


    @Autowired
    private EventController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    //private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }



	@Test
	public void getCustomerByCustIdTest() throws Exception
	{
		mockMvc.perform( MockMvcRequestBuilders
				.get("/findCust?id=1", 1)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(1));
	}

    @Test
    public void getCartByCustIdTest() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/findCart?id=1&custid=1", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.custId").value(1));
    }

    @Test
    public void getCustCartByCustIdTest() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/findCustCart?id=1", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(1));
    }

    @Test
    public void saveCustByCustIdTest() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/newcustomer")
                .content("{\"firstName\": \"Mary\",\"lastName\": \"Poppins\",\"homeTown\": \"New York\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); //expect a 200 Success Response
    }



}
