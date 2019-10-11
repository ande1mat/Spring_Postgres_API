package com.springjpa.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import com.springjpa.dto.CustomerCartDto;
import com.springjpa.dto.CustomerDto;
import com.springjpa.model.Customer;


@Mapper
public interface CustToCartMapper {

        //------http://mapstruct.org/documentation/stable/reference/html/#defining-mapper
        //Can have multiple interface methods with mapstruct interface below and call them individually.

        CustToCartMapper INSTANCE = Mappers.getMapper( CustToCartMapper.class );
        //source = Model, target = DTO
        @Mapping(source = "id", target = "id")
        @Mapping(source = "firstName", target = "firstname")
        @Mapping(source = "lastName", target = "lastname")

        //this is the method name of the interface that gets called 'custToCustomerDto'
        CustomerDto custToCustDto(Customer customer);

        //source = Model, target = DTO
        @Mapping(source = "firstName", target = "firstname")
        @Mapping(source = "lastName", target = "lastname")


        //this is the method name of the interface that gets called 'custToCustomerDto'
        CustomerCartDto custToCustomerCartDto(Customer customer);



}
