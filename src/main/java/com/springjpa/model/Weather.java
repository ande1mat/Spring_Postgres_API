package com.springjpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springjpa.dto.CloudsDto;
import org.springframework.data.redis.core.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RedisHash("Weather")
public class Weather implements Serializable {

    public String id;

    @JsonProperty("weather")
    private List<CloudsDto> weather;

    @JsonProperty("main")
    private Map<String, Object> main;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather1 = (Weather) o;
        return Objects.equals(id, weather1.id) &&
                Objects.equals(weather, weather1.weather) &&
                Objects.equals(main, weather1.main);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weather, main);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id='" + id + '\'' +
                ", weather=" + weather +
                ", main=" + main +
                '}';
    }
}
