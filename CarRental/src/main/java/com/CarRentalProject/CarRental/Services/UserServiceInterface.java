package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Admin;
import com.CarRentalProject.CarRental.Models.User;

public interface UserServiceInterface {

    User UpdateUser(User user);

    User addUser(User user);
    
    User findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);

    User findByEmailAndPassword(String email, String password);

    User findByUsernameOrEmail(String username, String email);

    User findByUsernameOrEmailAndPassword(String username, String email, String password);

    User findByUsernameOrEmailAndPasswordAndStatus(String username, String email, String password, String status);

    User findByUsernameOrEmailAndPasswordAndStatusNot(String username, String email, String password, String status);

    User findByUsernameOrEmailAndPasswordAndStatusNotIn(String username, String email, String password, String[] status);

    User findByUsernameOrEmailAndPasswordAndStatusIn(String username, String email, String password, String[] status);

    User findByUsernameOrEmailAndPasswordAndStatusNotInAndId(String username, String email, String password, String[] status, Integer id);

    Admin findByUsernameAdmin(String username);

    Admin findByEmailAdmin(String email);

    Admin findByUsernameAndPasswordAdmin(String username, String password);

    Admin findByEmailAndPasswordAdmin(String email, String password);

    Admin findByUsernameOrEmailAdmin(String username, String email);

    Admin findByUsernameOrEmailAndPasswordAdmin(String username, String email, String password);

    Admin findByUsernameOrEmailAndPasswordAndStatusAdmin(String username, String email, String password, String status);

    Admin findByUsernameOrEmailAndPasswordAndStatusNotAdmin(String username, String email, String password, String status);

    Admin findByUsernameOrEmailAndPasswordAndStatusNotInAdmin(String username, String email, String password, String[] status);

    Admin findByUsernameOrEmailAndPasswordAndStatusInAdmin(String username, String email, String password, String[] status);

    Admin findByUsernameOrEmailAndPasswordAndStatusNotInAndIdAdmin(String username, String email, String password, String[] status, Integer id);

}