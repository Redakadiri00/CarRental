package com.CarRentalProject.CarRental.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import com.CarRentalProject.CarRental.Models.UserModels.Client;
import com.CarRentalProject.CarRental.Services.UserServices.ClientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;




public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testCreateClient() throws Exception {
        Client client = new Client();
        client.setUsername("testuser");
        client.setPassword("password");
        client.setName("majidi");
        client.setEmail("mh.majidi.02@gmail.com");
        client.setDriversLicense("DL12345678");
        client.setLicenseExpiry(LocalDate.of(2025, 12, 31));
        client.setVerifiedDriver(true);
        client.setMembershipPoints(0);

        when(clientService.createClient(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"testuser\",\"password\":\"password\",\"name\":\"majidi\",\"email\":\"mh.majidi.02@gmail.com\",\"driversLicense\":\"DL12345678\",\"licenseExpiry\":\"2025-12-31\",\"verifiedDriver\":true,\"membershipPoints\":0}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetClient() throws Exception {
        Client client = new Client();
        client.setUsername("testuser");

        when(clientService.getClientByUsername("testuser")).thenReturn(client);

        mockMvc.perform(get("/api/clients")
                .param("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}