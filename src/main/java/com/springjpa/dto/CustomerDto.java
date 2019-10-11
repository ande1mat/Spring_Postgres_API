package com.springjpa.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * Created by z042183 on 7/15/19.
 */
@JsonPropertyOrder({ "id", "Firstname", "Lastname"})
public class CustomerDto {

    @JsonProperty("Id")  //This is the name that actually what gets returned in the JSON
    private Long id;

    @JsonProperty("Firstname")  //This is the name that actually what gets returned in the JSON
    private String firstname;

    @JsonProperty("Lastname") //This is the name that actually what gets returned in the JSON
    private String lastname;

    @JsonProperty("Hometown") //This is the name that actually what gets returned in the JSON
    private String hometown;

    @JsonProperty("Weather")
    private List<CloudsDto> weather;

    @JsonProperty("Main")
    //private Map<String, TempuratureDto> main;
    private Map<String, Object> main;

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
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

    public List<CloudsDto> getWeather() {
        return weather;
    }

    public void setWeather(List<CloudsDto> weather) {
        this.weather = weather;
    }


    public Map<String, Object> getMain() {
        return main;
    }

    public void setMain(Map<String, Object> main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", hometown='" + hometown + '\'' +
                ", weather=" + weather +
                ", main=" + main +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(hometown, that.hometown) &&
                Objects.equals(weather, that.weather) &&
                Objects.equals(main, that.main);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, hometown, weather, main);
    }
}
