package com.CarRentalProject.CarRental.DTO;

import lombok.Builder;

import java.time.LocalDate;

@Builder // Lombok annotation to automatically generate the following boilerplate code

public class ReservationDTO {
    private Integer vehiculeId;
    private Integer clientId;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Getters and setters
    public Integer getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Integer vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}