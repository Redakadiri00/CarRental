package com.CarRentalProject.CarRental.Mappers;

import com.CarRentalProject.CarRental.DTO.ClientDTO;
import com.CarRentalProject.CarRental.DTO.RegistryDTO;
import com.CarRentalProject.CarRental.Models.UserModels.Client;

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
    
    public Client toEntity(RegistryDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }
}
