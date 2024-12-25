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
import com.CarRentalProject.CarRental.Models.Admin;
import com.CarRentalProject.CarRental.Services.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminMapper adminMapper;

    @Autowired
    public AdminController(AdminService adminService, AdminMapper adminMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    }

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
