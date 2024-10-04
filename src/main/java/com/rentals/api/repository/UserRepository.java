package com.rentals.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rentals.api.model.DBUser;

public interface UserRepository extends CrudRepository<DBUser, Integer> {

    Optional<DBUser> findByEmail(String email);

}
