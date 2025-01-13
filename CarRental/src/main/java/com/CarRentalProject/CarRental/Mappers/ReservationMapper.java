package com.CarRentalProject.CarRental.Mappers;

import com.CarRentalProject.CarRental.DTO.ReservationDTO;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.Status_reservation;
import com.CarRentalProject.CarRental.Models.UserModels.User;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Models.Status_reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationDTO reservationDTO, Vehicule vehicule, User client) {
        Reservation reservation = new Reservation();

        reservation.setVehicule(vehicule);  // Associe le véhicule
        reservation.setClient(client);  // Associe le client

        reservation.setDateDebut(reservationDTO.getDateDebut());  // Date de début
        reservation.setDateFin(reservationDTO.getDateFin());  // Date de fin
        reservation.setDateReservation(LocalDate.now());  // Date de création de la réservation

        // Définit le statut initial
        reservation.setStatusReservation(Status_reservation.NonConfirmed);  // Par exemple, "En attente"

        return reservation;
    }

    public ReservationDTO toDTO(Reservation reservation) {
        return ReservationDTO.builder()
                .vehiculeId(reservation.getVehicule().getId_voiture())
                .clientId(reservation.getClient().getId())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .build();
    }
}
