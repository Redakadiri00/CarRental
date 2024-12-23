package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Repositories.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    //Regles metier
    public void checkContratDate(Contrat contrat){
        //Contrat doit avoir une date de creation
        if(contrat.getDateCreation() == null){
            throw new RuntimeException("Contrat doit avoir une date de creation");
        }
    }

    public void checkContratFacture(Contrat contrat){
        //Contrat doit avoir une facture
        if(contrat.getFacture() == null){
            throw new RuntimeException("Contrat doit avoir une facture");
        }
    }

    public void checkContrat(Contrat contrat){
        checkContratDate(contrat);
        checkContratFacture(contrat);
    }

    public Contrat saveContratWithRules(Contrat contrat){
        checkContrat(contrat);
        return contratRepository.save(contrat);
    }

    public Contrat updateContratWithRules(Long id, Contrat contrat){
        checkContrat(contrat);
        return updateContrat(id, contrat);
    }

    public Contrat genererContratWithRules(Facture facture) {
        Contrat contrat = new Contrat();
        contrat.setDateCreation(new Date());
        contrat.setFacture(facture); // Lier à la facture
        return saveContratWithRules(contrat);
    }





}
