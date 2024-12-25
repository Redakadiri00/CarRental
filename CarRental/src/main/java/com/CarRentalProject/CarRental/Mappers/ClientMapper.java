package com.CarRentalProject.CarRental.Mappers;

import com.CarRentalProject.CarRental.DTO.ClientDTO;
import com.CarRentalProject.CarRental.Models.Client;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper {
    
    private final ModelMapper modelMapper;
    
    public ClientDTO toDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
    
    public Client toEntity(ClientDTO dto) {
        return modelMapper.map(dto, Client.class);
    }
}
