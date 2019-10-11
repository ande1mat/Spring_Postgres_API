package com.springjpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.type.DoubleType;

/*
 * Created by z042183 on 12/09/18.
 *
 *
 *
 POSTGRES TABLE DDL for this Model
-- Table: public.cart

-- DROP TABLE public.cart;

CREATE TABLE public.cart
(
    id bigint NOT NULL,
    custid bigint NOT NULL,
    item character varying(40) COLLATE pg_catalog."default",
    item_description character varying(40) COLLATE pg_catalog."default",
    retail numeric(10,2),
    cartnumber bigint NOT NULL,
    CONSTRAINT cart_pkey PRIMARY KEY (id),
    CONSTRAINT fksigf5brtgghasucorb3kaqxda FOREIGN KEY (custid)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.cart
    OWNER to postgres;

 CREATE SEQUENCE hibernate_sequence_cart
 START WITH 1
 INCREMENT BY 1
 NO MAXVALUE
 NO MINVALUE
 CACHE 1;


 */

@Entity(name = "cart")
public class Cart implements Serializable {

    //Manage the hibernate PK sequence in Postgres
    private static final Long serialVersionUID = -3009157732242241606L;
    @Id
    @JsonIgnore
    @SequenceGenerator(name="identifier", sequenceName="hibernate_sequence_cart", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "custid")
    private Long custid;

    @Column(name = "item")
    private String item;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "retail", length = 10, precision = 2)
    private BigDecimal retail;

    @Column(name = "cartnumber")
    private Long cartnumber;

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }


    public Long getCartnumber() {
        return cartnumber;
    }

    public void setCartnumber(Long cartnumber) {
        this.cartnumber = cartnumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getRetail() {
        return retail;
    }

    public void setRetail(BigDecimal retail) {
        this.retail = retail;
    }



    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", custid=" + custid +
                ", item='" + item + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", retail=" + retail +
                ", cartnumber=" + cartnumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(custid, cart.custid) &&
                Objects.equals(item, cart.item) &&
                Objects.equals(itemDescription, cart.itemDescription) &&
                Objects.equals(retail, cart.retail) &&
                Objects.equals(cartnumber, cart.cartnumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, custid, item, itemDescription, retail, cartnumber);
    }
}
