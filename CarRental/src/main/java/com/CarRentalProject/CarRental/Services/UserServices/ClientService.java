package com.CarRentalProject.CarRental.Services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CarRentalProject.CarRental.Enums.UserStatus;
import com.CarRentalProject.CarRental.Models.UserModels.Client;
import com.CarRentalProject.CarRental.Repositories.UserRepositories.ClientRepository;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserService userService;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
    }

    public Client createClient(Client client) {
        validateClient(client);
        client.setMembershipPoints(0);
        return (Client) userService.createUser(client);
    }

    private void validateClient(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (clientRepository.findByUsername(client.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (clientRepository.findByDriversLicense(client.getDriversLicense()).isPresent()) {
            throw new IllegalArgumentException("Driver's license already exists");
        }
        if (client.getAddress() == null) {
            throw new IllegalArgumentException("Address must not be null");
        }
        if (client.getBirthdate() == null) {
            throw new IllegalArgumentException("Birthdate must not be null");
        }
        if (client.getLicenseExpiry() == null) {
            throw new IllegalArgumentException("License expiry date must not be null");
        }
    }

    public Client getClientById(int id) {
        try {
            return clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Client getClientByUsername(String username) {
        try {
            return clientRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Client getClientByEmail(String email) {
        try {
            return clientRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Client updateClient(Client client) {
        try {
            return clientRepository.save(client);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void deleteClient(Client client) {
        try {
            clientRepository.delete(client);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void deleteClientById(int id) {
        try {
            clientRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
