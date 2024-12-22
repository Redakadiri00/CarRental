package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
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

}
