# OpenClassroom Fullstack P03 - Implement a REST API with Spring Boot

## Project Overview

This API is designed to manage rentals with functionalities for CRUD operations utilizing Java, Spring Boot and MySQL.

It is designed to provide datas to an Angular web application which is available on [this github repo](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

## Requirements

* Java 17+
* Maven
* MySQL for database setup


## Setup

1. setup your secret key in env variable `${RENTAL_SECRET_KEY}`

1. Clone the repository.
2. Install dependencies with Maven:

```bash
mvn install
```

3. Create an empty database in MySQL, and grant a user the necessary rights to access and use that database.

4. Configure the following env variables:

* `${RENTAL_DB_NAME}`: the name of the configured database
* `${RENTAL_DB_USERNAME}`: the username of the created database user
* `${RENTAL_DB_PASSWORD}`: the password of the created database user

5. Initialize the database using Spring JPA by running the local server:

```bash
mvn spring-boot:run
```

## API Documentation

Access Swagger after starting the server at [localhost:9000/api/swagger-ui.html](http://localhost:9000/api/swagger-ui.html)