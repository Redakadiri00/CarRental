package com.CarRentalProject.CarRental.Models;

import jakarta.persistence.*;
import com.CarRentalProject.CarRental.Models.Reservation;
import java.util.Date;

@Entity
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @OneToOne
    @JoinColumn(name = "facture_id", nullable = false)
    private Facture facture; // Un contrat est lié à une facture.

    public Long getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(Long idContrat) {
        this.idContrat = idContrat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

  /*  public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }*/

    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat=" + idContrat +
                ", dateCreation=" + dateCreation +
                ", facture=" + facture +
                '}';
    }
}
