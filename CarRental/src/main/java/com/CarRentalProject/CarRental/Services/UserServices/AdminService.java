package com.CarRentalProject.CarRental.Services.UserServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CarRentalProject.CarRental.Enums.AdminLevel;
import com.CarRentalProject.CarRental.Enums.UserStatus;
import com.CarRentalProject.CarRental.Models.UserModels.Admin;
import com.CarRentalProject.CarRental.Repositories.UserRepositories.AdminRepository;

@Service
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserService userService;

    @Autowired
    public AdminService(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    public Admin createAdmin(Admin admin) {
        try {
            adminRepository.findByEmail(admin.getEmail()).ifPresent(a -> {
                throw new IllegalArgumentException("Email already exists");
            });
        } catch (IllegalArgumentException e) {
            throw e;
        }
        admin.setStatus(UserStatus.ACTIVE);
        System.out.println(admin);
        return (Admin) userService.createUser(admin);
    }

    public List<Admin> getAdminsByLevel(AdminLevel level) {
        try {
            return adminRepository.findByAdminLevel(level);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Admin getAdminByEmail(String email) {
        try {
            return adminRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Admin getAdminById(int id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin updateAdmin(Admin admin) {
        try {
            return adminRepository.save(admin);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void deleteAdmin(Admin admin) {
        try {
            adminRepository.delete(admin);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public List<Admin> getAdmins() {
        try {
            System.out.println(adminRepository.findAll());
            return adminRepository.findAll();
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
