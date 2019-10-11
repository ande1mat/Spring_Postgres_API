package com.springjpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.*;

/**
 * Created by z042183 on 9/16/18.
 */
@JsonPropertyOrder({ "Id", "Firstname", "Lastname"})
public class CustomerCartDto {

    @JsonProperty("Id")  //This is the name that actually what gets returned in the JSON
    private Long id;

    @JsonProperty("Firstname")  //This is the name that actually what gets returned in the JSON
    private String firstname;

    @JsonProperty("Lastname") //This is the name that actually what gets returned in the JSON
    private String lastname;

    private List<CartDto> Carts;


    public List<CartDto> getCarts() {
        return Carts;
    }

    public void setCarts(List<CartDto> Carts) {
        this.Carts = Carts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCartDto that = (CustomerCartDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(Carts, that.Carts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, Carts);
    }

    @Override
    public String toString() {
        return "CustomerCartDto{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", Carts=" + Carts +
                '}';
    }
}
