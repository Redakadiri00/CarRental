package com.CarRentalProject.CarRental.Mappers;

import com.CarRentalProject.CarRental.DTO.FactureDTO;
import com.CarRentalProject.CarRental.Enums.StatutFacture;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Models.Reservation;
import org.springframework.stereotype.Component;

@Component
public class FactureMapper {

    public FactureDTO toDTO(Facture facture) {
        return FactureDTO.builder()
                .id(facture.getIdFacture())
                .montantTotal(facture.getMontantTotal())
                .statut(facture.getStatut().toString())
                .reservationId(facture.getReservation().getId())
                .build();
    }

    public Facture fromDTO(FactureDTO factureDTO, Reservation reservation) {
        return Facture.builder()
                .montantTotal(factureDTO.getMontantTotal())
                .statut(StatutFacture.valueOf(factureDTO.getStatut()))
                .reservation(reservation)
                .build();
    }
}
