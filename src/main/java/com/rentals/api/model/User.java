package com.rentals.api.model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "A name must be provided")
    @NotEmpty(message = "Name must not be empty")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email")
    @NotNull(message = "An email must be provided")
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
