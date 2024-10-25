package com.rentals.api;

public class Exceptions {

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateUserException extends RuntimeException {
        public DuplicateUserException(String email) {
            super(String.format("User with email '%s' already exists.", email));
        }
    }
}
