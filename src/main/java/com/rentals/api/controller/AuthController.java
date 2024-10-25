package com.rentals.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.Exceptions.DuplicateUserException;
import com.rentals.api.dto.LoginDto;
import com.rentals.api.dto.RegisterDto;
import com.rentals.api.dto.TokenDto;
import com.rentals.api.dto.UserDto;
import com.rentals.api.model.User;
import com.rentals.api.service.JWTService;
import com.rentals.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "The Auth API. Contains all the operations that can be performed a Auth.")
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

        String email = loginData.getEmail();

        if (userservice.userExistsByEmail(email))
            throw new DuplicateUserException(email);

        userservice.createUser(new User(
                registerDatas.getName(),
                registerDatas.getEmail(),
                registerDatas.getPassword()));

        return login(loginData);
    }

    @Operation(summary = "Login a user")
    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(
            @Parameter(description = "username + password payload") @RequestBody LoginDto data) {

        var token = new UsernamePasswordAuthenticationToken(
                data.getEmail(),
                data.getPassword());

        if (!userservice.userExistsByEmail(data.getEmail()))
            throw new BadCredentialsException("Bad credentials");

        Authentication authentication = authenticationManager.authenticate(token);

        if (!authentication.isAuthenticated())
            throw new BadCredentialsException("Bad credentials");

        String jwt = jwtService.generateToken(data.getEmail());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new TokenDto(jwt));
    }

    @GetMapping("/auth/me")
    public ResponseEntity<Object> me() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                User user = (User) principal;

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new UserDto(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getCreated_at().toString(),
                                user.getUpdated_at().toString()));
            }
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new Object());
    }

    @GetMapping("/user/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getUser(@PathVariable Integer id) {

        User currentUser = userservice.getCurrentUser();

        UserDto userDTO = UserDto.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .created_at(currentUser.getCreated_at().toString())
                .updated_at(currentUser.getUpdated_at().toString())
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTO);
    }
}
