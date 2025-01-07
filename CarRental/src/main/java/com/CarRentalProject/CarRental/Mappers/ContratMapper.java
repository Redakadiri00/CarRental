package com.CarRentalProject.CarRental.Mappers;

import com.CarRentalProject.CarRental.DTO.ContratDTO;
import com.CarRentalProject.CarRental.Enums.StatutContrat;
import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Models.Facture;
import org.springframework.stereotype.Component;

@Component
public class ContratMapper {

    public ContratDTO toDTO(Contrat contrat) {
        return ContratDTO.builder()
                .id(contrat.getIdContrat())
                .DateCreation(contrat.getDateCreation())
                .statut(contrat.getStatut().toString())
                .factureId(contrat.getFacture().getIdFacture())
                .build();
    }

    public Contrat fromDTO(ContratDTO contratDTO, Facture facture) {
        return Contrat.builder()
                .dateCreation(contratDTO.getDateCreation())
                .statut(StatutContrat.valueOf(contratDTO.getStatut()))
                .facture(facture)
                .build();
    }
}
