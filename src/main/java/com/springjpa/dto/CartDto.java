package com.springjpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;

public class CartDto {

    @JsonProperty("Id")  //This is the name that actually what gets returned in the JSON
    private Long id;

    @JsonProperty("custId")  //This is the name that actually what gets returned in the JSON
    private Long custid;

    @JsonProperty("Item")  //This is the name that actually what gets returned in the JSON
    private String item;

    @JsonProperty("Item Description") //This is the name that actually what gets returned in the JSON
    private String itemDescription;

    @JsonProperty("Retail") //This is the name that actually what gets returned in the JSON
    private BigDecimal retail;

    @JsonProperty("Cart Number")  //This is the name that actually what gets returned in the JSON
    private Long cartnumber;

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

    @Override
    public String toString() {
        return "CartDto{" +
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
        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id) &&
                Objects.equals(custid, cartDto.custid) &&
                Objects.equals(item, cartDto.item) &&
                Objects.equals(itemDescription, cartDto.itemDescription) &&
                Objects.equals(retail, cartDto.retail) &&
                Objects.equals(cartnumber, cartDto.cartnumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, custid, item, itemDescription, retail, cartnumber);
    }
}