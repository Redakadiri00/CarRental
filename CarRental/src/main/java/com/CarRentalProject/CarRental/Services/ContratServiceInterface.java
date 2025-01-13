package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.DTO.ContratDTO;
import com.CarRentalProject.CarRental.DTO.FactureDTO;
import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Models.Facture;

import java.util.Date;
import java.util.List;

public interface ContratServiceInterface {

    // CRUD de base
    public List<ContratDTO> getAllContrats();
    public ContratDTO getContratById(Long id);
    public ContratDTO saveContrat(ContratDTO contrat);
    public void deleteContrat(Long id);
    public ContratDTO updateContrat(Long id, ContratDTO contrat);

    // Logique métier
    public ContratDTO genererContrat(Facture facture); // Génère un contrat à partir d'une facture
    public Contrat validerContrat(Long id); // Valide un contrat


    // Gestion des relations et filtrage
    public List<Contrat> getContratsByClientId(Long clientId); // Récupère les contrats d'un client
    public List<Contrat> getContratsByPeriode(Date debut, Date fin); // Récupère les contrats pour une période donnée

    // Génération de PDF
    public byte[] genererContratPdf(Long id); // Génère un PDF pour un contrat
}
