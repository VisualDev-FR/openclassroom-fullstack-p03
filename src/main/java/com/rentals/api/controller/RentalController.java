package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.model.Rental;
import com.rentals.api.service.RentalService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
public class RentalController {

    @Autowired
    RentalService rentalService;

    @PostMapping("/rentals")
    public ResponseEntity<Object> createRental(@Valid @RequestBody Rental rental) {

        rental = rentalService.createRental(rental);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rental);
    }

    @GetMapping("/rentals")
    public ResponseEntity<Object> getAllRentals() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(rentalService.getRentals());
    }
}
