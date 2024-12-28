package com.CarRentalProject.CarRental.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * Data Transfer Object for Client information.
 * The client's driver's license number.
 * Must consist of exactly 10 uppercase alphanumeric characters.
 * The expiration date of the client's driver's license.
 * Indicates whether the client's driver's license has been verified.
 * The number of membership points the client has accumulated.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends BaseDTO {
    @NotNull
    @Pattern(regexp = "^[A-Z0-9]{10}$") // Driver's license must be 10 characters
    private String driversLicense;
    
    @NotNull
    private LocalDate licenseExpiry;
    
    private Boolean verifiedDriver;
    private Integer membershipPoints;
}
