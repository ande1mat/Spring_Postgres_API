package com.springjpa;

import com.springjpa.dto.CartDto;
import com.springjpa.dto.CustomerDto;
import com.springjpa.model.Cart;
import com.springjpa.repository.CartRepository;
import com.springjpa.repository.CustomerRepository;
import com.springjpa.service.CustomerService;
import com.springjpa.model.Customer;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MockServiceUnitTests {

    @Mock
    private CustomerRepository daoCustomerMock;

    @Mock
    private CartRepository daoCartMock;

    @InjectMocks
    private CustomerService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testServiceFindCustomer() {

        int i=16;
        Long l = (long) i;

        when(daoCustomerMock.findByid(l)).thenReturn(Optional.of(new Customer()));
        CustomerDto customerdto = service.getCustById(l);
        assertEquals(customerdto, service.getCustById(l));
    }

    @Test
    public void testServiceSaveCustomer() {

        when(daoCustomerMock.save(any(Customer.class))).thenReturn(new Customer());
        Customer customer = new Customer();
        assertEquals(customer, service.saveCustomer(customer));
    }


    @Test
    public void testServiceDeleteCustomer() {

        int i=16;
        Long l = (long) i;

        when(daoCustomerMock.findByid(l)).thenReturn(Optional.of(new Customer()));
        service.deleteCustomer(l);
        verify(daoCustomerMock, times(1)).delete(new Customer());

    }


    @Test
    public void testServiceUpdateCustomer() {

        when(daoCustomerMock.findByid(1L)).thenReturn(Optional.of(new Customer()));
        Customer cust = service.updateCustomer(1L, "test", "test", "test");
        Customer customer = service.updateCustomer(1L, "test", "test", "test");
        Assertions.assertEquals(customer, cust);

    }


    @Test
    public void testServiceFindCart() {

        int i=16;
        Long l = (long) i;

        when(daoCartMock.findByIdAndCustid(l, l)).thenReturn(new Cart());
        CartDto cartdto = service.getCartByCustId(l,l);
        assertEquals(cartdto, service.getCartByCustId(l,l));

    }


    @Test
    public void testServiceSaveCart() {

        when(daoCartMock.save(any(Cart.class))).thenReturn(new Cart());
        Cart cart = new Cart();
        assertEquals(cart, service.saveCart(cart));

    }

    @Test
    public void testServiceDeleteCart() {

        int i=16;
        Long l = (long) i;

        when(daoCartMock.findByid(l)).thenReturn(new Cart());
        service.deleteCart(l);
        verify(daoCartMock, times(1)).delete(new Cart());

    }

}


