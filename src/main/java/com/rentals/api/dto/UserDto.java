package com.rentals.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    Integer id;
    String name;
    String email;
    String created_at;
    String updated_at;
}
