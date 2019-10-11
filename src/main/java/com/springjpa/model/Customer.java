package com.springjpa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * Created by z042183 on 5/28/18.
 *
 *
 *
 *  POSTGRES TABLE DDL for this Model
 -- Table: public.customer

 -- DROP TABLE public.customer;

 CREATE TABLE public.customer
 (
 id bigint NOT NULL,
 firstname character varying(20) COLLATE pg_catalog."default",
 lastname character varying(20) COLLATE pg_catalog."default",
 hometown character varying(40) COLLATE pg_catalog."default",
 CONSTRAINT customer_pkey PRIMARY KEY (id)
 )
 WITH (
 OIDS = FALSE
 )
 TABLESPACE pg_default;

 ALTER TABLE public.customer
 OWNER to postgres;

 CREATE SEQUENCE hibernate_sequence_customer
 START WITH 1
 INCREMENT BY 1
 NO MAXVALUE
 NO MINVALUE
 CACHE 1;

 *
 *
 *
 */

@Entity(name = "customer")

public class Customer implements Serializable {

    //Manage the hibernate PK sequence in Postgres
    private static final Long serialVersionUID = -3009157732242241606L;
    @Id
    @JsonIgnore
    @SequenceGenerator(name="identifier", sequenceName="hibernate_sequence_customer", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "hometown")
    private String homeTown;

    @OneToMany
    @JoinColumn(name="custid", referencedColumnName="id")
    private List<Cart> Carts;

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        id = id;
    }

    public List<Cart> getCarts() {
        return Carts;
    }

    public void setCarts(List<Cart> carts) {
        Carts = carts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(homeTown, customer.homeTown) &&
                Objects.equals(Carts, customer.Carts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, homeTown, Carts);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", homeTown='" + homeTown + '\'' +
                ", Carts=" + Carts +
                '}';
    }
}