package com.rentals.api.mapper;

import org.springframework.stereotype.Component;

import com.rentals.api.dto.RentalDto;
import com.rentals.api.dto.RentalResponse;
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;

@Component
public class RentalMapper {

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
