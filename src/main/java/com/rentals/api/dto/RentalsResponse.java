package com.rentals.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentalsResponse {
    private RentalResponse[] rentals;
}
