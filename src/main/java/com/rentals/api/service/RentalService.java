package com.rentals.api.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentals.api.Exceptions.ResourceNotFoundException;
import com.rentals.api.dto.RentalDto;
import com.rentals.api.dto.RentalResponse;
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;
import com.rentals.api.repository.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    FileStorageService fileStorageService;

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental createRental(RentalDto dto, User owner) throws IOException {

        String pictureUrl = null;

        if (dto.picture != null && !dto.picture.isEmpty()) {
            pictureUrl = fileStorageService.storeFile(dto.picture);
        }

        Rental rental = mapToRental(dto, owner, pictureUrl);

        return rentalRepository.save(rental);
    }

    public Rental getRentalByID(Integer id) {
        return rentalRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id " + id));
    }

    public Rental updateRental(Integer id, RentalDto dto) throws IOException {

        Rental rental = getRentalByID(id);

        if (dto.getPicture() != null && !dto.picture.isEmpty()) {
            String pictureUrl = fileStorageService.storeFile(dto.picture);
            rental.setPicture(pictureUrl);
        }

        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());

        return rentalRepository.save(rental);
    }

    public Rental mapToRental(RentalDto dto, User owner, String pictureUrl) {

        return Rental.builder()
                .name(dto.getName())
                .surface(dto.getSurface())
                .price(dto.getPrice())
                .picture(pictureUrl)
                .description(dto.getDescription())
                .owner(owner)
                .build();
    }

    public RentalResponse mapToRentalResponse(Rental rental) {

        return RentalResponse.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .owner_id(rental.getOwner().getId())
                .created_at(rental.getCreated_at().toString())
                .updated_at(rental.getUpdated_at().toString())
                .build();
    }
}
