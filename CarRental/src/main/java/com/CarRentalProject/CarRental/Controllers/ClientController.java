package com.CarRentalProject.CarRental.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.CarRentalProject.CarRental.DTOs.ClientDTO;
import com.CarRentalProject.CarRental.Mappers.ClientMapper;
import com.CarRentalProject.CarRental.Models.Client;
import com.CarRentalProject.CarRental.Services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody Client client) {
        try {
            Client clientCreated = clientService.createClient(client);
            System.out.println(clientCreated);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(clientMapper.toDTO(clientCreated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(null);
        }
    }

    @GetMapping
    public ResponseEntity<ClientDTO> getClient(@RequestParam String username) {
        try {
            Client client = clientService.getClientByUsername(username);
            return ResponseEntity.ok(clientMapper.toDTO(client));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(null);
        }
    }
}

