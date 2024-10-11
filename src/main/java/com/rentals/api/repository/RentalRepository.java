package com.rentals.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.rentals.api.model.Rental;

public interface RentalRepository extends CrudRepository<Rental, Integer> {

}
