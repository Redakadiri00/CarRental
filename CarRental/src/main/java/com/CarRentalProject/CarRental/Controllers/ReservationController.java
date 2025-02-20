package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.DTO.DateRangeDTO;
import com.CarRentalProject.CarRental.DTO.ReservationDTO;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Models.UserModels.User;
import com.CarRentalProject.CarRental.Services.ReservationService;
import com.CarRentalProject.CarRental.Services.VehiculeService;
import com.CarRentalProject.CarRental.Services.UserServices.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final VehiculeService vehiculeService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, VehiculeService vehiculeService, UserService userService) {
        this.reservationService = reservationService;
        this.vehiculeService = vehiculeService;
        this.userService = userService;
    }

    @PostMapping
    public Reservation makeReservation(@RequestBody ReservationDTO reservationDTO) {
        if (reservationDTO.getVehiculeId() == null || reservationDTO.getClientId() == null) {
            throw new IllegalArgumentException("Vehicule ID and Client ID must not be null");
        }

        Vehicule vehicule = vehiculeService.getVehiculeById(reservationDTO.getVehiculeId());
        User client = userService.getUserById(reservationDTO.getClientId());
       return  reservationService.Reserver(reservationDTO.getDateDebut(), reservationDTO.getDateFin(), vehicule, client);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.ListAllReservations();
    }

    @GetMapping("/ReservedDates/{vehiculeId}")
    public List<DateRangeDTO> getReservedDates(@PathVariable("vehiculeId") Integer vehiculeId) {
        return reservationService.getReservedDates(vehiculeId);
    };


}

