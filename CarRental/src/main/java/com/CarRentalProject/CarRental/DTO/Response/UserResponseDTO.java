package com.CarRentalProject.CarRental.DTO.Response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import com.CarRentalProject.CarRental.Enums.UserStatus;

@Data
@Builder
public class UserResponseDTO {
    private Integer id;
    private String email;
    private String username;
    private String name;
    private String role;
    private LocalDateTime createdAt;
    private String message;
    private Boolean isAuthenticated;
    private UserStatus status;
}
