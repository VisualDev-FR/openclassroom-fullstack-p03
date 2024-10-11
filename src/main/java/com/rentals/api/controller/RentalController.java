package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.model.Rental;
import com.rentals.api.service.RentalService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class RentalController {

    @Autowired
    RentalService rentalService;

    @PostMapping("/api/rentals")
    public ResponseEntity<Object> createRental(@RequestBody Rental rental) {

        rental = rentalService.createRental(rental);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rental);
    }
}
