# Spring_Postgres_API Project
Example of Springboot API connecting to Postgres DB

* [Local Setup](#local-setup)
* [API Endpoints](#api-endpoints)
* [MVP1 Features](#mvp1-features)
* [MVP2 Backlog Features](#mvp2-backlog-features)

## Local Setup 

This document provides guidelines and examples for White House Web APIs, encouraging consistency, maintainability, and best practices across applications. White House APIs aim to balance a truly RESTful API interface with a positive developer experience (DX).


## API Endpoints

These guidelines aim to support a truly RESTful API. Here are a few exceptions:
* Put the version number of the API in the URL (see examples below). Don’t accept any requests that do not specify a version number.
* Allow users to request formats like JSON or XML like this:
    * http://example.gov/api/v1/magazines.json
    * http://example.gov/api/v1/magazines.xml

## MVP1 Features

    * Create a Retail API for a Customer and their Cart details
    * Enable basic CRUD + Get functions to the Customer and Cart Postgres entities
    * Enable an external API call to return current weather conditions based on Customers hometown 
    * Create a custom mapper object for DTO/Model, and enable Mapstruct's mapper library
    * Create Health Endpoint
    * Add Unit Tests


## MVP2 Backlog Features
