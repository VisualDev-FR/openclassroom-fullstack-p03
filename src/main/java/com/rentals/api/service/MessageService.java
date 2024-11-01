package com.rentals.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentals.api.model.Message;
import com.rentals.api.repository.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}
