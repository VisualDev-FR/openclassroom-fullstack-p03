package com.rentals.api.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentals.api.Exceptions.ResourceNotFoundException;
import com.rentals.api.dto.RentalDto;
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

        if (dto.pictureContent != null && !dto.pictureContent.isEmpty()) {
            dto.picture = fileStorageService.storeFile(dto.pictureContent);
        }

        Rental rental = mapToRental(dto, owner);

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
            dto.setPicture(fileStorageService.storeFile(dto.pictureContent));
        }

        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(dto.getPicture());
        rental.setDescription(dto.getDescription());

        return rentalRepository.save(rental);
    }

    public Rental mapToRental(RentalDto dto, User owner) {

        return Rental.builder()
                .name(dto.getName())
                .surface(dto.getSurface())
                .price(dto.getPrice())
                .picture(dto.getPicture())
                .description(dto.getDescription())
                .owner(owner)
                .build();
    }

    public RentalDto mapToRentalDTO(Rental rental) {

        return RentalDto.builder()
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
