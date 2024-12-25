package com.CarRentalProject.CarRental.Models;

import com.CarRentalProject.CarRental.Enums.ModePaiement;
import com.CarRentalProject.CarRental.Enums.StatutFacture;
import com.CarRentalProject.CarRental.Models.Reservation;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    @Temporal(TemporalType.DATE) // Date sans l'heure (année, mois, jour) - TemporalType.TIMESTAMP pour avoir l'heure
    private Date dateFacturation;

    private Double montantTotal;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement; // Enumération pour les modes de paiement (CB, Chèque, Espèces)

    @Enumerated(EnumType.STRING)
    private StatutFacture statut;   // Enumération pour les statuts de facture (Payée, Non payée)


    @ManyToOne
    @JoinColumn(nullable = false)
    private Reservation reservation; // Une facture est liée à une réservation (relation ManyToOne)



    public Long getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Long idFacture) {
        this.idFacture = idFacture;
    }

    public Date getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(Date dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "idFacture=" + idFacture +
                ", dateFacturation=" + dateFacturation +
                ", montantTotal=" + montantTotal +
                ", modePaiement=" + modePaiement +
                ", statut=" + statut +
                '}';
    }
}