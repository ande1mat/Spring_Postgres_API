package com.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/*
http://localhost:8090
 */

@EnableCircuitBreaker
@SpringBootApplication
public class SpringJpaPostgreSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaPostgreSqlApplication.class, args);
	}
}
