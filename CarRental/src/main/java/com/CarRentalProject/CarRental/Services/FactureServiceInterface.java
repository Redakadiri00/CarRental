package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.DTO.ReservationDTO;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Models.Reservation;

import java.util.Date;
import java.util.List;

public interface FactureServiceInterface {

    // CRUD de base
    public List<Facture> getAllFactures();
    public Facture getFactureById(Long id);
    public Facture saveFacture(Facture facture);
    public void deleteFacture(Long id);
    public Facture updateFacture(Long id, Facture facture);

    // Logique métier
/*
    public Facture creerFactureAvecMontant(Reservation reservation); // Génère une facture avec montant
*/
    public Facture marquerCommePayee(Long id); // Marque la facture comme PAYEE
    public Double calculerMontantTotal(ReservationDTO reservation); // Calcule le montant total d'une facture
    /*Réutilisation :

    Si tu as besoin de calculer le montant sans créer une facture (par exemple, pour une simulation), tu peux utiliser calculerMontantTotal.
    creerFactureAvecMontant inclut cette logique de calcul mais l'étend pour couvrir les besoins de création de facture.*/

    // Gestion des relations et filtrage
    public List<Facture> getFacturesByClientId(Long clientId); // Récupère les factures d'un client
    public List<Facture> getFacturesByPeriode(Date debut, Date fin); // Récupère les factures pour une période donnée

    // Génération de PDF
    public byte[] genererFacturePdf(Long id); // Génère un PDF pour une facture
}
