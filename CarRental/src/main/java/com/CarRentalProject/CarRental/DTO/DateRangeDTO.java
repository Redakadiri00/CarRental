package com.CarRentalProject.CarRental.DTO;

import java.time.LocalDate;

public class DateRangeDTO {
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public DateRangeDTO(LocalDate dateDebut, LocalDate dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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