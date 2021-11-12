package com.springjpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.model.Weather;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, String> {}

