package com.rentals.api.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentals.api.Exceptions.ResourceNotFoundException;
import com.rentals.api.dto.RentalDto;
import com.rentals.api.mapper.RentalMapper;
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;
import com.rentals.api.repository.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private RentalMapper rentalMapper;

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental createRental(RentalDto dto, User owner) throws IOException {

        String pictureUrl = null;

        if (dto.picture != null && !dto.picture.isEmpty()) {
            pictureUrl = fileStorageService.storeFile(dto.picture);
        }

        Rental rental = rentalMapper.mapToRental(dto, owner, pictureUrl);

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

}
