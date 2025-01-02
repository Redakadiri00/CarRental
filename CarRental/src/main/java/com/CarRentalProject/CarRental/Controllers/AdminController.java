package com.CarRentalProject.CarRental.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.CarRentalProject.CarRental.DTO.AdminDTO;
import com.CarRentalProject.CarRental.Enums.AdminLevel;
import com.CarRentalProject.CarRental.Mappers.AdminMapper;
import com.CarRentalProject.CarRental.Models.UserModels.Admin;
import com.CarRentalProject.CarRental.Services.UserServices.AdminService;

import jakarta.validation.Valid;

/**
 * REST controller for managing Admin-related operations.
 */
@RestController
@RequestMapping("/api/admin")
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

    /**
     * Creates a new Admin.
     *
     * @param adminDTO the AdminDTO containing data for the new Admin
     * @return a ResponseEntity containing the created AdminDTO and HTTP status
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AdminDTO> createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        try {
            Admin admin = adminService.createAdmin(adminMapper.toEntity(adminDTO));
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(adminMapper.toDTO(admin));
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
}
