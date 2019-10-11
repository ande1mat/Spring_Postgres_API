package com.springjpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class TempuratureDto {


    @JsonProperty("temp") //This is the name that actually what gets returned in the JSON
    private BigDecimal temp;

    public BigDecimal getTemp() {
        return temp;
    }

    public void setTemp(BigDecimal temp) {
        this.temp = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempuratureDto that = (TempuratureDto) o;
        return Objects.equals(temp, that.temp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temp);
    }

    @Override
    public String toString() {
        return "TempuratureDto{" +
                "temp=" + temp +
                '}';
    }
}
