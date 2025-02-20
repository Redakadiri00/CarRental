package com.CarRentalProject.CarRental.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.CarRentalProject.CarRental.DTO.AdminDTO;
import com.CarRentalProject.CarRental.Enums.AdminLevel;
import com.CarRentalProject.CarRental.Mappers.AdminMapper;
import com.CarRentalProject.CarRental.Models.UserModels.Admin;
import com.CarRentalProject.CarRental.Services.UserServices.AdminService;

/**
 * REST controller for managing Admin-related operations.
 */
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;
    private final AdminMapper adminMapper;

    /**
     * Constructor for AdminController.
     *
     * @param adminService the service for handling Admin business logic
     * @param adminMapper  the mapper for converting Admin entities to DTOs
     */
    @Autowired
    public AdminController(AdminService adminService, AdminMapper adminMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAdmins() {
        try {
            List<Admin> admins = adminService.getAdmins();
            System.out.println(admins);
            List<AdminDTO> adminDTOs = admins.stream()
                    .map(adminMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(adminDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    /**
     * Creates a new Admin.
     *
     * @param adminDTO the AdminDTO containing data for the new Admin
     * @return a ResponseEntity containing the created AdminDTO and HTTP status
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> createAdmin(@RequestBody Admin admin) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Admin currentAdmin = adminService.getAdminByUsername(username);
            if (currentAdmin == null || currentAdmin.getAdminLevel() != AdminLevel.SUPER_ADMIN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to create an admin");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You are not authorized to create an admin");
        }
        try {
            Admin adminCreated = adminService.createAdmin(admin);
            if (adminCreated == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Admin creation failed");
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Admin created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Customize error response if needed
        }
    }

    /**
     * Retrieves all Admins by their admin level.
     *
     * @param level the AdminLevel to filter Admins by
     * @return a ResponseEntity containing a list of AdminDTOs and HTTP status
     */
    @GetMapping("/level/{level}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminDTO>> getAdminsByLevel(@PathVariable AdminLevel level) {
        try {
            List<Admin> admins = adminService.getAdminsByLevel(level);
            List<AdminDTO> adminDTOs = admins.stream()
                    .map(adminMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(adminDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Customize error response if needed
        }
    }

    /**
     * Deletes an Admin by their ID.
     * 
     * @param id the ID of the Admin to delete
     * @return a ResponseEntity containing the HTTP status
     * @throws IllegalArgumentException if the Admin is not found
     * 
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> deleteAdmin(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Admin currentAdmin = adminService.getAdminByUsername(username);
            if (currentAdmin == null || currentAdmin.getAdminLevel() != AdminLevel.SUPER_ADMIN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to delete an admin");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You are not authorized to delete an admin");
        }
        try {
            Admin admin = adminService.getAdminById(id);
            if (admin == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Admin not found");
            }
            adminService.deleteAdmin(admin);
            return ResponseEntity.ok("Admin deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Customize error response if needed
        }
    }
}
