package com.springjpa.mapper;

import com.springjpa.dto.CartDto;
import com.springjpa.dto.CustomerCartDto;
import com.springjpa.dto.CustomerDto;
import com.springjpa.model.Cart;
import com.springjpa.model.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-12T13:36:48-0600",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
public class CustToCartMapperImpl implements CustToCartMapper {

    @Override
    public CustomerDto custToCustDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setFirstname( customer.getFirstName() );
        customerDto.setId( customer.getid() );
        customerDto.setLastname( customer.getLastName() );

        return customerDto;
    }

    @Override
    public CustomerCartDto custToCustomerCartDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerCartDto customerCartDto = new CustomerCartDto();

        customerCartDto.setFirstname( customer.getFirstName() );
        customerCartDto.setLastname( customer.getLastName() );
        customerCartDto.setCarts( cartListToCartDtoList( customer.getCarts() ) );
        customerCartDto.setId( customer.getid() );

        return customerCartDto;
    }

    protected CartDto cartToCartDto(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDto cartDto = new CartDto();

        cartDto.setItem( cart.getItem() );
        cartDto.setItemDescription( cart.getItemDescription() );
        cartDto.setRetail( cart.getRetail() );
        cartDto.setId( cart.getId() );
        cartDto.setCustid( cart.getCustid() );
        cartDto.setCartnumber( cart.getCartnumber() );

        return cartDto;
    }

    protected List<CartDto> cartListToCartDtoList(List<Cart> list) {
        if ( list == null ) {
            return null;
        }

        List<CartDto> list1 = new ArrayList<CartDto>( list.size() );
        for ( Cart cart : list ) {
            list1.add( cartToCartDto( cart ) );
        }

        return list1;
    }
}
