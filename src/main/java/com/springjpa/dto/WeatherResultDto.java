package com.springjpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class WeatherResultDto {

    @JsonProperty("weather")
    private List<CloudsDto> weather;

    @JsonProperty("main")
    private Map<String, Object> main;
    //private Map<String, TempuratureDto> main;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherResultDto that = (WeatherResultDto) o;
        return Objects.equals(weather, that.weather) &&
                Objects.equals(main, that.main);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weather, main);
    }

    @Override
    public String toString() {
        return "WeatherResultDto{" +
                "weather=" + weather +
                ", main=" + main +
                '}';
    }
}
