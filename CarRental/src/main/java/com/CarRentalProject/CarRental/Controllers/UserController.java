package com.CarRentalProject.CarRental.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.CarRentalProject.CarRental.Models.User;
import com.CarRentalProject.CarRental.Services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        try {

            System.out.println(user);
            userService.addUser(user);
            return "User added successfully";
        } catch (Exception e) {
            return "Error adding user: " + e.getMessage();
        }
    }

    @PostMapping("/update")
    public String updateUser(@RequestBody User user) {
        try {
            userService.UpdateUser(user);
            return "User updated successfully";
        } catch (Exception e) {
            return "Error updating user: " + e.getMessage();
        }
    }
}
