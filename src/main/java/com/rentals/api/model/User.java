package com.rentals.api.model;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails {

    public static final int NAME_MAX_SIZE = 255;

    public static final int EMAIL_MAX_SIZE = 255;

    public static final int PASSWORD_MAX_SIZE = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = NAME_MAX_SIZE)
    private String name;

    @Column(nullable = false, length = EMAIL_MAX_SIZE, unique = true)
    private String email;

    @Column(nullable = false, length = PASSWORD_MAX_SIZE)
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private Date created_at;

    @UpdateTimestamp
    private Date updated_at;

    @OneToMany(mappedBy = "owner")
    private Set<Rental> rentals;

    @OneToMany(mappedBy = "user")
    private Set<Message> messages;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
