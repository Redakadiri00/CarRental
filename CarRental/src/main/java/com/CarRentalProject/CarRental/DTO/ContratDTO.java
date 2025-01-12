package com.CarRentalProject.CarRental.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ContratDTO {
    private Long id;
    private Date DateCreation;
    private String statut;
    private Long factureId; // Pour associer une facture
}