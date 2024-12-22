package com.CarRentalProject.CarRental.Services;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    // Add methods related to security logic here

    public boolean authenticate(String username, String password) {
        // Implement authentication logic
        return true;
    }

    public boolean authorize(String username, String role) {
        // Implement authorization logic
        return true;
    }
}
