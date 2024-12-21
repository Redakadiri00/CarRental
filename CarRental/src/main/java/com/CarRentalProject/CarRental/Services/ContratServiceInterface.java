package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Contrat;
import java.util.List;

public interface ContratServiceInterface {
    List<Contrat> getAllContrats();
    Contrat getContratById(Long id);
    Contrat saveContrat(Contrat contrat);
    void deleteContrat(Long id);
    Contrat updateContrat(Long id, Contrat contrat);
}
