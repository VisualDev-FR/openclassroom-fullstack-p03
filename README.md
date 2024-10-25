# OpenClassroom Fullstack P03 - Implement a REST API with Spring Boot

## Project Overview

This API is designed to manage rentals with functionalities for CRUD operations utilizing Java, Spring Boot and MySQL.

It is designed to provide datas to an Angular web application which is available on [this github repo](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

## Requirements

* Java 17+
* Maven
* MySQL for database setup

## Setup

1. Clone the repository.
2. Install dependencies with Maven:

```bash
mvn install
```
2. Configure the database settings in [application.properties](./src/main/resources/application.properties):

```ini
# mySQLConfiguration
spring.datasource.url=jdbc:mysql://localhost:3306/<your_db_name>
spring.datasource.username=<your_db_username>
spring.datasource.password=<your_db_password>
```

3. Initialize the database using Spring JPA by running the local server:

```bash
mvn spring-boot:run
```

## API Documentation

Access Swagger after starting the server at [localhost:9000/api/swagger-ui.html](http://localhost:9000/api/swagger-ui.html)