package com.rentals.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rentals.api.repository.UserRepository;
import com.rentals.api.model.DBUser;

import lombok.Data;

@Data
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<DBUser> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<DBUser> getUsers() {
        return userRepository.findAll();
    }

    public DBUser createUser(DBUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<DBUser> user = getUserByEmail(username);

        if (!user.isPresent())
            throw new UsernameNotFoundException("user not found");

        return user.get();
    }

}
