package com.CarRentalProject.CarRental.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private String role;
    private String message;
}
