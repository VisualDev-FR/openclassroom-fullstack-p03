package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.dto.LoginDto;
import com.rentals.api.dto.TokenDto;
import com.rentals.api.model.DBUser;
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

    @PostMapping("/api/auth/register")
    public ResponseEntity<Object> register(@Valid @RequestBody DBUser user, BindingResult bindingResult) {

        // send error response if the given user datas are not valid
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getAllErrors());
        }

        // store the user credentials to retreive the token later
        LoginDto loginData = new LoginDto(user.getEmail(), user.getPassword());

        try {
            userservice.createUser(user);
        }
        // send error response if user already exists
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("this user already exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("error: %s", e.getClass()));
        }

        return getToken(loginData);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<Object> getToken(@RequestBody LoginDto data) {

        var token = new UsernamePasswordAuthenticationToken(
                data.getEmail(),
                data.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(data.getEmail());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new TokenDto(jwt));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid username or password");
    }

}
