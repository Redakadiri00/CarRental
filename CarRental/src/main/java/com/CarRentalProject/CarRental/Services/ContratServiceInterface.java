package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Models.Facture;

import java.util.Date;
import java.util.List;

public interface ContratServiceInterface {

    // CRUD de base
    public List<Contrat> getAllContrats();
    public Contrat getContratById(Long id);
    public Contrat saveContrat(Contrat contrat);
    public void deleteContrat(Long id);
    public Contrat updateContrat(Long id, Contrat contrat);

    // Logique métier
    public Contrat genererContrat(Facture facture); // Génère un contrat à partir d'une facture
    public Contrat validerContrat(Long id); // Valide un contrat
    public boolean verifierFacturesPayees(Contrat contrat); // Vérifie si toutes les factures associées sont payées

    // Gestion des relations et filtrage
    public List<Contrat> getContratsByClientId(Long clientId); // Récupère les contrats d'un client
    public List<Contrat> getContratsByPeriode(Date debut, Date fin); // Récupère les contrats pour une période donnée

    // Génération de PDF
    public byte[] genererContratPdf(Long id); // Génère un PDF pour un contrat
}
