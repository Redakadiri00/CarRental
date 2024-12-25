package com.CarRentalProject.CarRental.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CarRentalProject.CarRental.Enums.AdminLevel;
import com.CarRentalProject.CarRental.Enums.UserStatus;
import com.CarRentalProject.CarRental.Models.Admin;
import com.CarRentalProject.CarRental.Repositories.AdminRepository;

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
        admin.setStatus(UserStatus.ACTIVE);
        return (Admin) userService.createUser(admin);
    }

    public List<Admin> getAdminsByLevel(AdminLevel level) {
        return adminRepository.findByAdminLevel(level);
    }

    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }

    public Admin getAdminById(int id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin updateAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }
}
