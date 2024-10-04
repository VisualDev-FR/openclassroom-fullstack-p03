package com.rentals.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.rentals.api.model.User;;

public interface UserRepository extends CrudRepository<User, Integer> {

}
