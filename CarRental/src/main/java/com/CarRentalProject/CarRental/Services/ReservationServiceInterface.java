package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.User;
import com.CarRentalProject.CarRental.Models.Vehicule;

import java.time.LocalDate;
import java.util.List;

public interface ReservationServiceInterface {

    //public void addReservation(Reservation reservation);
    public Reservation Reserver(LocalDate dateDebut, LocalDate dateFin, Vehicule vehicule, User client);
    public List<Reservation> ListAllReservations();
}
