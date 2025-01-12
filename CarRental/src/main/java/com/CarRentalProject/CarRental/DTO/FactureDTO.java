package com.CarRentalProject.CarRental.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // Lombok annotation to automatically generate the following boilerplate code
public class FactureDTO {
    private Long id;
    private double montantTotal;
    private String statut;
    private int reservationId; // Pour associer une réservation
}