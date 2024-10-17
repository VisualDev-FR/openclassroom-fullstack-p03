package com.rentals.api.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<RentalDto[]> getAllRentals() {

        RentalDto[] rentals = StreamSupport.stream(rentalService.getRentals().spliterator(), false)
                .map(rentalService::mapToRentalDTO)
                .toArray(RentalDto[]::new);

        return ResponseEntity.status(HttpStatus.OK)
                .body(rentals);
    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<Object> rentalByID(@PathVariable Integer id) {

        Rental rental = rentalService.getRentalByID(id);
        RentalDto dto = rentalService.mapToRentalDTO(rental);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<Object> updateRental(@PathVariable Integer id, @Valid @RequestBody RentalDto rentalData) {

        Rental updatedRental = rentalService.updateRental(id, rentalData);
        RentalDto result = rentalService.mapToRentalDTO(updatedRental);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}