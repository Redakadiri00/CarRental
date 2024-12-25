package com.CarRentalProject.CarRental.DTO.Response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Integer id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private String message;
}
