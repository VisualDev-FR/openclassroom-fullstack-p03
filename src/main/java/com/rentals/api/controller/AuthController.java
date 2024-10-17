package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.dto.LoginDto;
import com.rentals.api.dto.RegisterDto;
import com.rentals.api.dto.TokenDto;
import com.rentals.api.model.User;
import com.rentals.api.service.JWTService;
import com.rentals.api.service.UserService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userservice;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDatas) {

        LoginDto loginData = new LoginDto(registerDatas.getEmail(), registerDatas.getPassword());

        userservice.createUser(new User(
                registerDatas.getName(),
                registerDatas.getEmail(),
                registerDatas.getPassword()));

        return login(loginData);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto data) {

        var token = new UsernamePasswordAuthenticationToken(
                data.getEmail(),
                data.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(data.getEmail());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new TokenDto(jwt));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }

}
