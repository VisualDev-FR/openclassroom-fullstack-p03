package com.rentals.api.dto;

import com.rentals.api.model.Message;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageDto {

    private Integer id;

    private Integer user_id;

    @NotNull
    private Integer rental_id;

    @NotNull()
    @NotEmpty()
    @Size(max = Message.MESSAGE_MAX_SIZE)
    private String message;

    private String created_at;

    private String updated_at;
}
