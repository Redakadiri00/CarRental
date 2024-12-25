package com.CarRentalProject.CarRental.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends BaseDTO {
    @NotNull
    @Pattern(regexp = "^[A-Z0-9]{10}$")
    private String driversLicense;
    
    @NotNull
    private LocalDate licenseExpiry;
    
    private Boolean verifiedDriver;
    private Integer membershipPoints;

    //test
}
