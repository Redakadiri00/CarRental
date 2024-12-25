package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Enums.StatutContrat;
import com.CarRentalProject.CarRental.Enums.StatutFacture;
import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Repositories.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfWriter;
/*import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;*/
import java.io.ByteArrayOutputStream;

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
        existingContrat.setStatut(contrat.getStatut());
        return contratRepository.save(existingContrat);
    }

 /*   //Regles metier
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
    }*/

    public Contrat genererContrat(Facture facture) {
        // Génère un contrat à partir d'une facture
        Contrat contrat = new Contrat();
        contrat.setDateCreation(new Date());
        contrat.setFacture(facture);
        contrat.setStatut(StatutContrat.EN_COURS);
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat validerContrat(Long id) {
        Contrat contrat = contratRepository.findById(id).orElse(null);
        // Validation du contrat
        return contratRepository.save(contrat);
    }

    @Override
    public boolean verifierFacturesPayees(Contrat contrat) {
        if (contrat.getFacture() == null) {
            throw new RuntimeException("Aucune facture associée au contrat.");
        }

        // Vérifie si la facture associée est payée
        return contrat.getFacture().getStatut() == StatutFacture.PAYEE;
    }
    @Override
    public List<Contrat> getContratsByClientId(Long clientId) {
        return contratRepository.findByFactureReservationClientId(clientId);
    }

    @Override
    public List<Contrat> getContratsByPeriode(Date debut, Date fin) {
        return contratRepository.findByDateCreationBetween(debut, fin);    }

    @Override
    public byte[] genererContratPdf(Long id) {
        return new byte[0];
    }


}
