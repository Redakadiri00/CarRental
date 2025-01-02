package com.CarRentalProject.CarRental.Services.UserServices;

import com.CarRentalProject.CarRental.Models.UserModels.User;

public interface UserServiceInterface {

    User updateUser(User user);

    User addUser(User user);

    Boolean deleteUser(User user);

    Iterable<User> getAllUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByUsernameAndPassword(String username, String password);

    User getUserByEmailAndPassword(String email, String password);
    
    User findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);

    User findByEmailAndPassword(String email, String password);
}