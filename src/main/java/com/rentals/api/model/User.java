package com.rentals.api.model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/*
    CREATE TABLE `USERS` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `email` varchar(255),
    `name` varchar(255),
    `password` varchar(255),
    `created_at` timestamp,
    `updated_at` timestamp
    );
*/

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Name must not be empty")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email")
    @NotEmpty(message = "Email must not be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "A password must be provided")
    @NotEmpty(message = "Password must not be empty")
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private Date created_at;

    @UpdateTimestamp
    private Date updated_at;
}
