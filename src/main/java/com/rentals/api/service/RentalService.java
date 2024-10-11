package com.rentals.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentals.api.model.Rental;
import com.rentals.api.repository.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }
}
