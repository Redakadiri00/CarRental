package com.CarRentalProject.CarRental.DTO;

import com.CarRentalProject.CarRental.Enums.AdminLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) for Admin.
 * Extends the BaseDTO class.
 * 
 * This class is used to transfer admin-related data between processes.
 * It includes the admin level which is required.
 * 
 * Annotations:
 * - @Data: Generates getters, setters, toString, equals, and hashCode methods.
 * - @EqualsAndHashCode(callSuper = true): Includes the properties of the superclass in the equals and hashCode methods.
 * - @NotNull: Ensures that the adminLevel field is not null and provides a custom message if the validation fails.
 * 
 * Fields:
 * - adminLevel (AdminLevel): The level of the admin, which is required.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDTO extends BaseDTO {
    @NotNull(message = "Admin level is required")
    private AdminLevel adminLevel;
}
