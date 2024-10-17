package com.rentals.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Optional<Rental> getRentalByID(Integer id) {
        return rentalRepository.findById(id);
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
