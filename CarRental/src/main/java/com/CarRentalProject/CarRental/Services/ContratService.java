package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Repositories.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratService implements ContratServiceInterface {

    @Autowired
    private ContratRepository contratRepository;

    @Override
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat getContratById(Long id) {
        return contratRepository.findById(id).orElse(null);
    }

    @Override
    public Contrat saveContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public void deleteContrat(Long id) {
        contratRepository.deleteById(id);
    }

    @Override
    public Contrat updateContrat(Long id, Contrat contrat) {
        Contrat existingContrat = contratRepository.findById(id).orElse(null);
        existingContrat.setDateCreation(contrat.getDateCreation());
        existingContrat.setFacture(contrat.getFacture());
        return contratRepository.save(existingContrat);
    }
}
