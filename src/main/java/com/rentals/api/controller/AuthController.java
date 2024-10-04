package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.dto.LoginDto;
import com.rentals.api.model.DBUser;
import com.rentals.api.service.JWTService;
import com.rentals.api.service.UserService;

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
    public DBUser register(@RequestBody DBUser user) {
        return userservice.createUser(user);
    }

    @PostMapping("/auth/token")
    public String getToken(@RequestBody LoginDto data) {

        var token = new UsernamePasswordAuthenticationToken(
                data.getEmail(),
                data.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(data.getEmail());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }

    }

}
