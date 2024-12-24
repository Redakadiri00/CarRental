package com.CarRentalProject.CarRental.Models;

import java.time.temporal.Temporal;
import java.util.Date;

public class Reservation {
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private Date dateReservation;
    private Status_reservation statusReservation;
    private Vehicule vehicule;
    //private Client client;

    public Reservation() {
    }

    public Reservation(int id, Date dateDebut, Date dateFin, Date dateReservation, Status_reservation statusReservation, Vehicule vehicule) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateReservation = dateReservation;
        this.statusReservation = statusReservation;
        this.vehicule = vehicule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Temporal getDateDebut() {
        return (Temporal) dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Temporal getDateFin() {
        return (Temporal) dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Status_reservation getStatusReservation() {
        return statusReservation;
    }

    public void setStatusReservation(Status_reservation statusReservation) {
        this.statusReservation = statusReservation;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

}
