package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
    Admin findByUsername(String username);

    Admin findByEmail(String email);

    Admin findByUsernameAndPassword(String username, String password);

    Admin findByEmailAndPassword(String email, String password);

    Admin findByUsernameOrEmail(String username, String email);

    Admin findByUsernameOrEmailAndPassword(String username, String email, String password);

    Admin findByUsernameOrEmailAndPasswordAndStatus(String username, String email, String password, String status);

    Admin findByUsernameOrEmailAndPasswordAndStatusNot(String username, String email, String password, String status);

    Admin findByUsernameOrEmailAndPasswordAndStatusNotIn(String username, String email, String password, String[] status);

    Admin findByUsernameOrEmailAndPasswordAndStatusIn(String username, String email, String password, String[] status);

    Admin findByUsernameOrEmailAndPasswordAndStatusNotInAndId(String username, String email, String password, String[] status, Integer id);

}
