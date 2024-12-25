package com.CarRentalProject.CarRental.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.CarRentalProject.CarRental.DTOs.ClientDTO;
import com.CarRentalProject.CarRental.Enums.UserStatus;
import com.CarRentalProject.CarRental.Models.Client;
import com.CarRentalProject.CarRental.Repositories.ClientRepository;

@SpringBootTest
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateClient_Success() {
        // Arrange
        Client client = new Client();
        client.setId(1);
        client.setEmail("client@example.com");
        client.setUsername("clientuser");
        client.setName("Client User");
        client.setDriversLicense("DL12345678"); // 10 characters
        client.setLicenseExpiry(LocalDate.of(2025, 12, 31));
        client.setVerifiedDriver(true);
        client.setMembershipPoints(100);
        client.setStatus(UserStatus.ACTIVE);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail("client@example.com");
        clientDTO.setUsername("clientuser");
        clientDTO.setName("Client User");
        clientDTO.setDriversLicense("DL12345678");
        clientDTO.setLicenseExpiry(LocalDate.of(2025, 12, 31));
        clientDTO.setVerifiedDriver(true);
        clientDTO.setMembershipPoints(100);

        when(clientRepository.findByDriversLicense(anyString())).thenReturn(Optional.empty());
        when(userService.createUser(any(Client.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client createdClient = clientService.createClient(client);

        // Assert
        assertNotNull(createdClient);
        assertEquals("client@example.com", createdClient.getEmail());
        assertEquals("clientuser", createdClient.getUsername());
        assertEquals("Client User", createdClient.getName());
        assertEquals("DL12345678", createdClient.getDriversLicense());
        assertEquals(LocalDate.of(2025, 12, 31), createdClient.getLicenseExpiry());
        assertTrue(createdClient.getVerifiedDriver());
        assertEquals(100, createdClient.getMembershipPoints());
        assertEquals(UserStatus.ACTIVE, createdClient.getStatus());

        verify(clientRepository, times(1)).findByDriversLicense("DL12345678");
        verify(userService, times(1)).createUser(any(Client.class));
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    public void testCreateClient_DuplicateDriversLicense() {
        // Arrange
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail("client@example.com");
        clientDTO.setUsername("clientuser");
        clientDTO.setName("Client User");
        clientDTO.setDriversLicense("DL12345678");
        clientDTO.setLicenseExpiry(LocalDate.of(2025, 12, 31));
        clientDTO.setVerifiedDriver(true);
        clientDTO.setMembershipPoints(100);

        Client existingClient = new Client();
        existingClient.setDriversLicense("DL12345678");

        when(clientRepository.findByDriversLicense("DL12345678")).thenReturn(Optional.of(existingClient));

        // Act & Assert
        Exception exception = assertThrows(DriverLicenseAlreadyExistsException.class, () -> {
            clientService.createClient(clientMapperToEntity(clientDTO));
        });

        String expectedMessage = "Driver's license already exists: DL12345678";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(clientRepository, times(1)).findByDriversLicense("DL12345678");
        verify(userService, times(0)).createUser(any(Client.class));
        verify(clientRepository, times(0)).save(any(Client.class));
    }

    @Test
    public void testGetClientById() {
        // Arrange
        Client client = new Client();
        client.setId(1);
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        // Act
        Client foundClient = clientService.getClientById(1);

        // Assert
        assertNotNull(foundClient);
        assertEquals(1, foundClient.getId());
        verify(clientRepository, times(1)).findById(1);
    }

    @Test
    public void testGetClientByUsername() {
        // Arrange
        Client client = new Client();
        client.setUsername("clientuser");
        when(clientRepository.findByUsername("clientuser")).thenReturn(Optional.of(client));

        // Act
        Client foundClient = clientService.getClientByUsername("clientuser");

        // Assert
        assertNotNull(foundClient);
        assertEquals("clientuser", foundClient.getUsername());
        verify(clientRepository, times(1)).findByUsername("clientuser");
    }

    @Test
    public void testGetClientByEmail() {
        // Arrange
        Client client = new Client();
        client.setEmail("client@example.com");
        when(clientRepository.findByEmail("client@example.com")).thenReturn(Optional.of(client));

        // Act
        Client foundClient = clientService.getClientByEmail("client@example.com");

        // Assert
        assertNotNull(foundClient);
        assertEquals("client@example.com", foundClient.getEmail());
        verify(clientRepository, times(1)).findByEmail("client@example.com");
    }

    @Test
    public void testUpdateClient() {
        // Arrange
        Client client = new Client();
        client.setId(1);
        client.setEmail("updated@example.com");
        when(clientRepository.save(client)).thenReturn(client);

        // Act
        Client updatedClient = clientService.updateClient(client);

        // Assert
        assertNotNull(updatedClient);
        assertEquals("updated@example.com", updatedClient.getEmail());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void testDeleteClient() {
        // Arrange
        Client client = new Client();
        client.setId(1);

        // Act
        clientService.deleteClient(client);

        // Assert
        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    public void testDeleteClientById() {
        // Arrange
        int clientId = 1;

        // Act
        clientService.deleteClientById(clientId);

        // Assert
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    // Helper method to convert ClientDTO to Client for testing purposes
    private Client clientMapperToEntity(ClientDTO dto) {
        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setUsername(dto.getUsername());
        client.setName(dto.getName());
        client.setDriversLicense(dto.getDriversLicense());
        client.setLicenseExpiry(dto.getLicenseExpiry());
        client.setVerifiedDriver(dto.getVerifiedDriver());
        client.setMembershipPoints(dto.getMembershipPoints());
        return client;
    }

    // Custom exception for duplicate driver's license
    public static class DriverLicenseAlreadyExistsException extends RuntimeException {
        public DriverLicenseAlreadyExistsException(String driversLicense) {
            super("Driver's license already exists: " + driversLicense);
        }
    }
}