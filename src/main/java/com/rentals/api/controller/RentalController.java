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
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;
import com.rentals.api.service.FileStorageService;
import com.rentals.api.service.RentalService;
import com.rentals.api.service.UserService;

@RestController
public class RentalController {

    @Autowired
    RentalService rentalService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UserService userService;

    @PostMapping("/rentals")
    public ResponseEntity<RentalDto> createRental(@ModelAttribute("rentals") RentalDto dto) throws IOException {

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        RentalDto rentalDatas = RentalDto.builder()
                .name(dto.name)
                .surface(dto.surface)
                .price(dto.price)
                .description(dto.description)
                .build();

        if (dto.picture != null) {
            rentalDatas.setPicture(dto.picture);
        }

        Rental createdRental = rentalService.createRental(rentalDatas, currentUser);
        RentalDto result = rentalService.mapToRentalDTO(createdRental);

        // result.setPicture(rentalDatas.picture);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
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
    public ResponseEntity<Object> updateRental(
            @PathVariable Integer id,
            @ModelAttribute("rentals") RentalDto rentalDatas) throws IOException {

        Rental rental = rentalService.getRentalByID(id);
        User currentUser = userService.getCurrentUser();

        if (rental.owner.getId() != currentUser.getId())
            throw new AccessDeniedException("You are not allowed to update rental " + id);

        Rental updatedRental = rentalService.updateRental(id, rentalDatas);
        RentalDto result = rentalService.mapToRentalDTO(updatedRental);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}