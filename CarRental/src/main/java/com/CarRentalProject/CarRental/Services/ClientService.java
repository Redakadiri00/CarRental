package com.CarRentalProject.CarRental.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CarRentalProject.CarRental.DTO.ClientDTO;
import com.CarRentalProject.CarRental.Enums.UserStatus;
import com.CarRentalProject.CarRental.Models.Client;
import com.CarRentalProject.CarRental.Repositories.ClientRepository;

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

    public Client createClient(Client clientDTO) {
        validateDriversLicense(clientDTO);
        clientDTO.setStatus(UserStatus.INACTIVE);
        clientDTO.setMembershipPoints(0);
        return (Client) userService.createUser(clientDTO);
    }

    private void validateDriversLicense(Client client) {
        try {
            clientRepository.findByDriversLicense(client.getDriversLicense()).ifPresent(c -> {
                throw new IllegalArgumentException("Driver's license already exists");
            });
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Client getClientById(int id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client getClientByUsername(String username) {
        return clientRepository.findByUsername(username).orElse(null);
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public void deleteClientById(int id) {
        clientRepository.deleteById(id);
    }
}
