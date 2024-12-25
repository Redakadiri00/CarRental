package com.CarRentalProject.CarRental.Mappers;

import com.CarRentalProject.CarRental.DTOs.AdminDTO;
import com.CarRentalProject.CarRental.Models.Admin;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {
    
    private final ModelMapper modelMapper;
    
    public AdminDTO toDTO(Admin admin) {
        return modelMapper.map(admin, AdminDTO.class);
    }
    
    public Admin toEntity(AdminDTO dto) {
        return modelMapper.map(dto, Admin.class);
    }
}
