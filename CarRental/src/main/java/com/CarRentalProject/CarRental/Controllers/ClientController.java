package com.CarRentalProject.CarRental.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.CarRentalProject.CarRental.DTO.ClientDTO;
import com.CarRentalProject.CarRental.Mappers.ClientMapper;
import com.CarRentalProject.CarRental.Models.UserModels.Client;
import com.CarRentalProject.CarRental.Services.UserServices.ClientService;

import jakarta.validation.Valid;

/**
 * REST controller for managing Client-related operations.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    /**
     * Constructor for ClientController.
     *
     * @param clientService the service for handling Client business logic
     * @param clientMapper  the mapper for converting Client entities to DTOs
     */
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    /**
     * Creates a new Client.
     *
     * @param client the Client entity to be created, validated for constraints
     * @return a ResponseEntity containing the created ClientDTO and HTTP status
     */
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

    /**
     * Retrieves a Client by their username.
     *
     * @param username the username of the Client to retrieve
     * @return a ResponseEntity containing the ClientDTO and HTTP status
     */
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
