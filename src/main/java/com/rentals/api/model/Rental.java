package com.rentals.api.model;

import java.sql.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    public static final int NAME_MAX_SIZE = 255;

    public static final int IMG_URL_MAX_SIZE = 255;

    public static final int DESC_MAX_SIZE = 2000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(nullable = false, length = NAME_MAX_SIZE)
    public String name;

    @Column(nullable = false)
    public Integer surface;

    @Column(nullable = false)
    public Float price;

    @Column(length = IMG_URL_MAX_SIZE)
    public String picture;

    @Column(nullable = false, length = DESC_MAX_SIZE)
    public String description;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "user_id")
    public User owner;

    @CreationTimestamp
    @Column(updatable = false)
    public Date created_at;

    @UpdateTimestamp
    public Date updated_at;

    @OneToMany(mappedBy = "rental")
    private Set<Message> messages;
}
