package com.rentals.api.controller;

import java.util.List;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.dto.RentalDto;
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;
import com.rentals.api.service.RentalService;

import jakarta.validation.Valid;

@RestController
public class RentalController {

    @Autowired
    RentalService rentalService;

    @PostMapping("/rentals")
    public ResponseEntity<RentalDto> createRental(@Valid @RequestBody RentalDto rentalDatas) {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Rental createdRental = rentalService.mapToRental(rentalDatas, currentUser);

        createdRental = rentalService.createRental(createdRental);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rentalService.mapToRentalDTO(createdRental));
    }

    @GetMapping("/rentals")
    public ResponseEntity<Object> getAllRentals() {

        List<RentalDto> rentals = StreamSupport.stream(rentalService.getRentals().spliterator(), false)
                .map(rentalService::mapToRentalDTO)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(rentals);
    }
}