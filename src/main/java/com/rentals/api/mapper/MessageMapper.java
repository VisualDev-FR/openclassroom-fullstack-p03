package com.rentals.api.mapper;

import org.springframework.stereotype.Component;

import com.rentals.api.dto.MessageDto;
import com.rentals.api.model.Message;
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;

@Component
public class MessageMapper {

    public Message mapToMessage(MessageDto dto, User user, Rental rental) {
        return Message.builder()
                .user(user)
                .rental(rental)
                .message(dto.getMessage())
                .build();
    }

    public MessageDto mapToMessageDTO(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .user_id(message.getUser().getId())
                .rental_id(message.getRental().getId())
                .message(message.getMessage())
                .created_at(message.getCreated_at().toString())
                .updated_at(message.getUpdated_at().toString())
                .build();
    }
}
