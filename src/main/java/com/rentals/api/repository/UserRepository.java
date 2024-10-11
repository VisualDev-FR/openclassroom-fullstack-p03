package com.rentals.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rentals.api.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
