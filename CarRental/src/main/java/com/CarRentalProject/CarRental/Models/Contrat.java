package com.CarRentalProject.CarRental.Models;

import com.CarRentalProject.CarRental.Enums.StatutContrat;
import com.CarRentalProject.CarRental.Enums.StatutFacture;
import jakarta.persistence.*;
import com.CarRentalProject.CarRental.Models.Reservation;
import java.util.Date;

@Entity
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    @Enumerated(EnumType.STRING)
    private StatutContrat statut;   // Enumération pour les statuts de contrat (En cours, Terminé)

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @OneToOne
    @JoinColumn(nullable = false)
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

    public StatutContrat getStatut() {
        return statut;
    }

    public void setStatut(StatutContrat statut) {
        this.statut = statut;
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
                ", statut=" + statut +
                ", dateCreation=" + dateCreation +
                ", facture=" + facture +
                '}';
    }
}
