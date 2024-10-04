package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.model.User;
import com.rentals.api.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/Users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public @ResponseBody String createUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping()
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}