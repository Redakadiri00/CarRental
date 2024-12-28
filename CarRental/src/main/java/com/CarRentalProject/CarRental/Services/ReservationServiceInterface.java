package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Models.UserModels.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationServiceInterface {

    //public void addReservation(Reservation reservation);
    public void Reserver(LocalDate dateDebut, LocalDate dateFin, Vehicule vehicule, User client);
    public List<Reservation> ListAllReservations();
}
