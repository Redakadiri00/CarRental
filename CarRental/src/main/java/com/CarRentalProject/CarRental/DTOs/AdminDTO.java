package com.CarRentalProject.CarRental.DTOs;

import com.CarRentalProject.CarRental.Enums.AdminLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDTO extends BaseDTO {
    @NotNull(message = "Admin level is required")
    private AdminLevel adminLevel;
}
