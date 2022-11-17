# Spring_Postgres_API Project
Example of Springboot API connecting to Postgres DB

* [Local Setup](#local-setup)
* [API Endpoints](#api-endpoints)
* [MVP1 Features](#MVP1-Features:)
* [MVP2 Features](#MVP2-Features:)
* [Backlog Features](#Future-ToDos)

## Local Setup 

These steps enable you to run the app locally within your IDE.  
* IDE and Postgres Setup:

* Step 1:
    * Setup a local or hosted Postgres Server
    * Use the Postgres configurations in the Application Properties for database name and credentials
    * Run the DDL scripts in the Models Customer and Cart files
    
* Step 2:
    * I changed this from a Maven based Springboot application to Gradle.  So you can enter these commands in the project directory:
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

## MVP1 Features

    * Created a Retail API for a Customer and their Cart details
    * Enabled basic CRUD + Get functions to the Customer and Cart Postgres entities
    * Enabled an external API call to return current weather conditions based on Customers hometown 
    * Created a custom mapper object for DTO/Model, and enable Mapstruct's mapper library
    * Created Health Endpoint
    * Added Unit Tests / Test Suite
    
## MVP2 Features

    * Added a Redis Cache for the weather API response for citys
    * Added a Hystrix Curcuit breaker on the external Weather API call
    * Upgraded Gradle Version and Build Gradle to work with Spring 2 and Java 11
    * Added Controller Advice and Global Exception Handling
    * Updated Unit and Integration Tests
    * Expanded Actuator Health Checks to include Postgres and Redis

## Future ToDos
    * Add no Cart found exception handling
    * Update Test Suite to work with Unit 5
    * Create a Docker Image of the API, and run it on a Docker Container, connecting to Postgres localhost
    * Beef up unit test coverage from ~60% of lines covered to ~90%
    * Add endpoint for filtering by Cust Id and Cart Number (http://localhost:8080/findCart?id=11&custid=16)
    * Add Delete by Cart Number endpoint
    * Clean up the code to have better naming, camelCase, and add java Docs
    * Add Micrometer Metrics 
    * Add Logger/Logback out to file or Elastic
    * Setup Deployment Pipeline to K8s (Minikube) or Cloud Env using Drone, and add Secrets, etc.
    
   
