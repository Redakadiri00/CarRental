package com.CarRentalProject.CarRental.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Base Data Transfer Object (DTO) class that provides common fields for all DTOs.
 * This class is intended to be extended by other DTO classes.
 * 
 * Fields:
 * - id: Unique identifier for the DTO.
 * - email: Email address associated with the DTO.
 * - username: Username associated with the DTO.
 * - name: Name associated with the DTO.
 * - phone: Phone number associated with the DTO.
 * - createdAt: Timestamp indicating when the DTO was created.
 * - updatedAt: Timestamp indicating when the DTO was last updated.
 */
@Data
public abstract class BaseDTO {
    private Integer id;
    private String email;
    private String username;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String address;
    private LocalDate birthdate;
}
