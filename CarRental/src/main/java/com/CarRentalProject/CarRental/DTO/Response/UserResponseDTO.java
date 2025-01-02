package com.CarRentalProject.CarRental.DTO.Response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

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
}
