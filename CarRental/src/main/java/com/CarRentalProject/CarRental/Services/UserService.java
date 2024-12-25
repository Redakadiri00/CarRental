package com.CarRentalProject.CarRental.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CarRentalProject.CarRental.Models.User;
import com.CarRentalProject.CarRental.Repositories.UserRepository;

import jakarta.persistence.EntityExistsException;

import com.CarRentalProject.CarRental.Config.PasswordHashing;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        validateNewUser(user);
        user.setPassword(PasswordHashing.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    private void validateNewUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityExistsException("Email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityExistsException("Username already exists");
        }
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
