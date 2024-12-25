package com.CarRentalProject.CarRental.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public abstract class BaseDTO {
    private Integer id;
    private String email;
    private String username;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
