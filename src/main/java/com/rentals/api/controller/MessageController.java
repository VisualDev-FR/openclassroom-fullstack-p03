package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.dto.MessageDto;
import com.rentals.api.model.Message;
import com.rentals.api.model.Rental;
import com.rentals.api.model.User;
import com.rentals.api.service.MessageService;
import com.rentals.api.service.RentalService;
import com.rentals.api.service.UserService;

import jakarta.validation.Valid;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private RentalService rentalService;

    @PostMapping("/messages")
    public ResponseEntity<MessageDto> createMessage(@Valid @RequestBody MessageDto messageData) {

        User currentUser = userService.getCurrentUser();
        Rental rental = rentalService.getRentalByID(messageData.getRental_id());

        Message createdMessage = messageService.mapToMessage(messageData, currentUser, rental);

        createdMessage = messageService.createMessage(createdMessage);
        messageData = messageService.mapToMessageDTO(createdMessage);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(messageData);
    }
}
