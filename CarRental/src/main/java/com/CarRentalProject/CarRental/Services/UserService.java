package com.CarRentalProject.CarRental.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CarRentalProject.CarRental.Models.Admin;
import com.CarRentalProject.CarRental.Models.User;
import com.CarRentalProject.CarRental.Repositories.UserRepository;

@Service
public class UserService implements UserServiceInterface {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return null;
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return null;
    }

    @Override
    public User findByUsernameOrEmailAndPassword(String username, String email, String password) {
        return null;
    }

    @Override
    public User findByUsernameOrEmailAndPasswordAndStatus(String username, String email, String password, String status) {
        return null;
    }

    @Override
    public User findByUsernameOrEmailAndPasswordAndStatusNot(String username, String email, String password, String status) {
        return null;
    }

    @Override
    public User findByUsernameOrEmailAndPasswordAndStatusNotIn(String username, String email, String password, String[] status) {
        return null;
    }

    @Override
    public User findByUsernameOrEmailAndPasswordAndStatusIn(String username, String email, String password, String[] status) {
        return null;
    }

    @Override
    public User findByUsernameOrEmailAndPasswordAndStatusNotInAndId(String username, String email, String password, String[] status, Integer id) {
        return null;
    }

    @Override
    public Admin findByUsernameAdmin(String username) {
        return null;
    }

    @Override
    public Admin findByEmailAdmin(String email) {
        return null;
    }

    @Override
    public Admin findByUsernameAndPasswordAdmin(String username, String password) {
        return null;
    }

    @Override
    public Admin findByEmailAndPasswordAdmin(String email, String password) {
        return null;
    }

    @Override
    public Admin findByUsernameOrEmailAdmin(String username, String email) {
        return null;
    }

    @Override
    public Admin findByUsernameOrEmailAndPasswordAdmin(String username, String email, String password) {
        return null;
    }

    @Override
    public Admin findByUsernameOrEmailAndPasswordAndStatusAdmin(String username, String email, String password, String status) {
        return null;
    }

    @Override
    public Admin findByUsernameOrEmailAndPasswordAndStatusNotAdmin(String username, String email, String password, String status) {
        return null;
    }

    @Override
    public Admin findByUsernameOrEmailAndPasswordAndStatusNotInAdmin(String username, String email, String password, String[] status) {
        return null;
    }

    @Override
    public Admin findByUsernameOrEmailAndPasswordAndStatusInAdmin(String username, String email, String password, String[] status) {
        return null;
    }

    @Override
    public Admin findByUsernameOrEmailAndPasswordAndStatusNotInAndIdAdmin(String username, String email,
            String password, String[] status, Integer id) {
        return null;
    }

    @Override
    public User UpdateUser(User user) {

        User userToUpdate = userRepository.findById(user.getId()).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setAdress(user.getAdress());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setBirthdate(user.getBirthdate());
        userToUpdate.setStatus(user.getStatus());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        return userRepository.save(userToUpdate);
    }

    @Override
    public User addUser(User user) {
        // Optional: Add validation checks (e.g., username or email uniqueness)
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }
}
