package com.CarRentalProject.CarRental.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) class for user registration details.
 * This class is used to encapsulate the password required for user registration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegistryDTO extends ClientDTO {

    private String password;
}
