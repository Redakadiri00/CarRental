package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Repositories.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Déclare la classe comme un bean de service Spring
public class FactureService {

    @Autowired // Injecte le bean FactureRepository ici pour pouvoir l'utiliser dans cette classe (injection de dépendance)
    private FactureRepository factureRepository;

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Facture getFactureById(Long id) {
        return factureRepository.findById(id).orElse(null);
    }

    public Facture saveFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }

    public Facture updateFacture(Long id, Facture facture) {
        Facture existingFacture = factureRepository.findById(id).orElse(null);
        existingFacture.setDateFacturation(facture.getDateFacturation());
        existingFacture.setMontantTotal(facture.getMontantTotal());
        existingFacture.setModePaiement(facture.getModePaiement());
        existingFacture.setStatut(facture.getStatut());
//        existingFacture.setReservation(facture.getReservation());
        return factureRepository.save(existingFacture);
    }
}
