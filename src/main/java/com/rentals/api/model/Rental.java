package com.rentals.api.model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @NotNull()
    @NotEmpty()
    @Column(nullable = false)
    public String name;

    @NotNull()
    @NotEmpty()
    @Column(nullable = false)
    public Integer surface;

    @NotNull()
    @NotEmpty()
    @Column(nullable = false, length = 255)
    public Float price;
    public String picture;

    @NotNull()
    @NotEmpty()
    @Column(nullable = false, length = 2000)
    public String description;

    @NotNull()
    @NotEmpty()
    @ManyToOne()
    @JoinColumn(nullable = false, name = "owner_id")
    public User owner;

    @CreationTimestamp
    @Column(updatable = false)
    public Date created_at;

    @UpdateTimestamp
    public Date updated_at;
}
