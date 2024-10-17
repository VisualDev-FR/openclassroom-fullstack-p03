package com.rentals.api.dto;

import com.rentals.api.model.Rental;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RentalDto {

    @NotNull()
    @NotEmpty()
    @Size(max = Rental.NAME_MAX_SIZE)
    public String name;

    @NotNull()
    @NotEmpty()
    public Integer surface;

    @NotNull()
    @NotEmpty()
    public Float price;

    @NotNull()
    @NotEmpty()
    @Size(max = Rental.DESC_MAX_SIZE)
    public String description;

    @Size(max = Rental.PICTURE_MAX_SIZE)
    public String picture;
}
