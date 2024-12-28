package com.CarRentalProject.CarRental.DTO;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for authentication details.
 * This class is used to encapsulate the username and password
 * required for authentication purposes.
 */
@Data
@Builder
public class AuthDTO {
    private String username;
    private String password;
}
