package com.rentals.api.controller;

import java.io.IOException;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.dto.RentalDto;
import com.rentals.api.dto.RentalResponse;
import com.rentals.api.dto.RentalsResponse;
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;
import com.rentals.api.service.FileStorageService;
import com.rentals.api.service.RentalService;
import com.rentals.api.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Rental", description = "TODOC: rental controller")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class RentalController {

    @Autowired
    RentalService rentalService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UserService userService;

    @PostMapping("/rentals")
    public ResponseEntity<RentalResponse> createRental(@ModelAttribute("rentals") RentalDto dto) throws IOException {

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Rental createdRental = rentalService.createRental(dto, currentUser);
        RentalResponse result = rentalService.mapToRentalResponse(createdRental);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @GetMapping("/rentals")
    public ResponseEntity<RentalsResponse> getAllRentals() {

        RentalResponse[] rentals = StreamSupport.stream(rentalService.getRentals().spliterator(), false)
                .map(rentalService::mapToRentalResponse)
                .toArray(RentalResponse[]::new);

        RentalsResponse response = RentalsResponse.builder()
                .rentals(rentals)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalResponse> rentalByID(@PathVariable Integer id) {

        Rental rental = rentalService.getRentalByID(id);
        RentalResponse dto = rentalService.mapToRentalResponse(rental);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<RentalResponse> updateRental(
            @PathVariable Integer id,
            @ModelAttribute("rentals") RentalDto rentalDatas) throws IOException {

        Rental rental = rentalService.getRentalByID(id);
        User currentUser = userService.getCurrentUser();

        if (rental.owner.getId() != currentUser.getId())
            throw new AccessDeniedException("You are not allowed to update rental " + id);

        Rental updatedRental = rentalService.updateRental(id, rentalDatas);
        RentalResponse result = rentalService.mapToRentalResponse(updatedRental);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}