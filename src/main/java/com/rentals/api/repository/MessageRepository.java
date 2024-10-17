package com.rentals.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.rentals.api.model.Message;
import com.rentals.api.model.Rental;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    Iterable<Message> findByRental(Rental rental);
}
