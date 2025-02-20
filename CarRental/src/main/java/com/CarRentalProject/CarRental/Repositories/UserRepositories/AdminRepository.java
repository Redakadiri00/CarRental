package com.CarRentalProject.CarRental.Repositories.UserRepositories;

import com.CarRentalProject.CarRental.Enums.AdminLevel;
import com.CarRentalProject.CarRental.Models.UserModels.Admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByEmail(String email);
    List<Admin> findByAdminLevel(AdminLevel level);
    Optional<Admin> findByUsername(String username);
}
