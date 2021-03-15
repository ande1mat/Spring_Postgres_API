# Spring_Postgres_API Project
Example of Springboot API connecting to Postgres DB

* [Local Setup](#local-setup)
* [API Endpoints](#api-endpoints)
* [MVP1 Features](#mvp1-features)
* [MVP2 Backlog Features](#mvp2-backlog-features)

## Local Setup 

These steps enable you to run the app locally within your IDE.  In MVP#2 i will provide a docker container and steps to run locally outside of the IDE.
* IDE and Postgres Setup:

* Step 1:
    * Setup a local or hosted Postgres Server
    * Use the Postgres configurations in the Application Properties for database name and credentials
    * Run the DDL scripts in the Models Customer and Cart files
    
* Step 2:
    * I changed this from a Maven based Spring boot application to Gradle.  So you can enter these commands in the project directory:
    * --> gradle --version //Check your gradle version, if not present install it
    * --> ./gradlew clean build //Compile the application code, run the test, and package it as determined by your build.gradle
    * --> ./gradlew bootRun //start up the service.  Ctl-C to stop the service
    * OR
          * Import to your favorite IDE - IntelliJ and run from there if you choose.
    
    * Run the application listening on port http://localhost:8080/ set in the Application properties
    * Check the Health Endpoint status should be 'UP' http://localhost:8080/actuator/health


## API Endpoints


* Get a Customer Information and Hometown Weather: http://localhost:8080/findCust?id=#

{
    "Id": 16,
    "Firstname": "Lucky",
    "Lastname": "Customer",
    "Hometown": "Minneapolis",
    "Weather": [
        {
            "main": "Clouds",
            "description": "overcast clouds"
        }
    ],
    "Main": {
        "temp": 39.09,
        "pressure": 1007,
        "humidity": 60,
        "temp_min": 37.4,
        "temp_max": 41
    }
}

* Get a Cart for a Customer: http://localhost:8080/findCart?id=#&custid=#

{
    "Id": 11,
    "custId": 16,
    "Item": "Lucky Test 500",
    "Item Description": "Very Lucky Toy 500",
    "Retail": 500.50,
    "Cart Number": 3
}

* Get all the Carts for a Customer: http://localhost:8080/findCustCart?id=#

{
    "Id": 6,
    "Firstname": "Lucky",
    "Lastname": "Six",
    "carts": [
        {
            "Id": 1,
            "custId": 6,
            "Item": "Big Cat Bulldozer",
            "Item Description": "Toy Bulldozer",
            "Retail": 10.25,
            "Cart Number": 1
        },
        {
            "Id": 2,
            "custId": 6,
            "Item": "Pokemon Big Pack",
            "Item Description": "Pokemon Pack",
            "Retail": 9.99,
            "Cart Number": 1
        }
    ]
}

* Save new Customer: http://localhost:8080/newcustomer

* Save new Cart for Customer: http://localhost:8080/newcart

* Update Customer: http://localhost:8080/updatecustomer

* Update Cart: http://localhost:8080/updatecart

* Delete Customer: http://localhost:8080/deletecustomer

* Delete Cart: http://localhost:8080/deletecart

NOTE: I have added a Postman Collection in this Repo for each of the above endpoints you can import and use yourself

## MVP1 Features (in the current Release)

    * Create a Retail API for a Customer and their Cart details
    * Enable basic CRUD + Get functions to the Customer and Cart Postgres entities
    * Enable an external API call to return current weather conditions based on Customers hometown 
    * Create a custom mapper object for DTO/Model, and enable Mapstruct's mapper library
    * Create Health Endpoint
    * Add Unit Tests / Test Suite


## MVP2+ Backlog Features

    * Move from Maven to Gradle (Completed)
    * Create a Docker Image of the API, and run it on a Docker Container, connecting to Postgres localhost
    * Enable Async multithreading of GET requests from clients and test it.  E.g. https://dzone.com/articles/multi-threading-in-spring-boot-using-completablefu
    * Look at circuit breaker patterns using hystrix library 
    * Add Rest Retry logic - e.g. https://dzone.com/articles/spring-retry-way-to-handle-failures
    * Look at using more spring annotations to reduce self written code (such as getter-setter, tostring, equals, hashcode )     * Look at built validations in pojo objects
    * Add exception handling (search git repo for GlobalExceptionHandler) to handle HTTP exceptions for all REST endpoints
    * Look into health actuator library
    * Beef up unit test coverage from 60% of lines covered to 90%
    * Add endpoint for filtering by Cust Id and Cart Number (http://localhost:8080/findCart?id=11&custid=16)
    * Add Delete by Cart Number endpoint
    * Clean up the code to have better naming, camelCase, and add java Docs
    * Add Metrics 
    * Add Logger/Logback out to file or Elastic
    * Add Custom Error Handling in Event Controller 
    * Setup Deployment Pipeline to K8s (Minikube) or Cloud Env using Drone, and add Secrets, etc.
    
    


