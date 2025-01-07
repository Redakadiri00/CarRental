package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.DTO.DateRangeDTO;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.Status_reservation;
import com.CarRentalProject.CarRental.Models.UserModels.User;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Models.UserModels.User;
import com.CarRentalProject.CarRental.Repositories.ReservationRepository;
import com.CarRentalProject.CarRental.Repositories.VehiculeRepository;
import com.CarRentalProject.CarRental.Repositories.UserRepositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService implements ReservationServiceInterface {

    private final ReservationRepository reservationRepository;
    private final VehiculeRepository vehiculeRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, VehiculeRepository vehiculeRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.userRepository = userRepository;
    }


    public Reservation Reserver(LocalDate dateDebut, LocalDate dateFin, Vehicule vehicule, User client) {
        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException("Date de debut doit etre avant la date de fin");
        }

        List<Reservation> existingReservations = reservationRepository.findReservationsByVehiculeAndDateRange(vehicule, dateDebut, dateFin);
        if (!existingReservations.isEmpty()) {
            throw new IllegalArgumentException("Vehicule is not available for the selected dates");
        }

        Reservation reservation = new Reservation();
        reservation.setVehicule(vehicule);
        reservation.setClient(client);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setDateReservation(LocalDate.now());
        reservation.setStatusReservation(Status_reservation.NonConfirmed);

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> ListAllReservations() {
        return reservationRepository.findAll();
    }

    public List<DateRangeDTO> getReservedDates(Integer vehiculeId) {
        return reservationRepository.findReservedDatesByVehiculeId(vehiculeId);
    }

}