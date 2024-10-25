package com.rentals.api.dto;

import org.springframework.web.multipart.MultipartFile;

import com.rentals.api.model.Rental;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentalDto {

    public Integer id;

    @NotNull()
    @NotEmpty()
    @Size(max = Rental.NAME_MAX_SIZE)
    public String name;

    @NotNull()
    public Integer surface;

    @NotNull()
    public Float price;

    public MultipartFile pictureContent;

    @Size(max = Rental.IMG_URL_MAX_SIZE)
    public String picture;

    @NotNull()
    @NotEmpty()
    @Size(max = Rental.DESC_MAX_SIZE)
    public String description;

    public String created_at;

    public String updated_at;

    public Integer owner_id;
}
