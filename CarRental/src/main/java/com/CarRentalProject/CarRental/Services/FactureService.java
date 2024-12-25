package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Enums.StatutFacture;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Repositories.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
/*import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;*/
import java.io.ByteArrayOutputStream;

@Service // Déclare la classe comme un bean de service Spring
public class FactureService implements FactureServiceInterface {

    @Autowired // Injecte le bean FactureRepository ici pour pouvoir l'utiliser dans cette classe (injection de dépendance)
    private FactureRepository factureRepository;

    @Autowired
    private ContratService contratService;

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

    public Facture creerFactureAvecMontant(Reservation reservation) { // Génère une facture avec montant à partir d'une réservation donnée
        Facture facture = new Facture();
        facture.setDateFacturation(new Date());

        // Calcul du montant total
        Double montantTotal = calculerMontantTotal(reservation);
        facture.setMontantTotal(montantTotal);

        // Statut initial
        facture.setStatut(StatutFacture.NON_PAYEE);

        // Relation avec la réservation
        facture.setReservation(reservation);

        Facture savedFacture = factureRepository.save(facture);

        // Générer automatiquement un contrat
        contratService.genererContrat(savedFacture);

        return savedFacture;
    }

    @Override
    public Facture marquerCommePayee(Long id) {
        Facture facture = factureRepository.findById(id).orElse(null);
        facture.setStatut(StatutFacture.PAYEE);
        return factureRepository.save(facture);
    }

    @Override
    public Double calculerMontantTotal(Reservation reservation) {
        LocalDate localDateDebut = reservation.getDateDebut();
        LocalDate localDateFin = reservation.getDateFin();

        long duree = ChronoUnit.DAYS.between(localDateDebut, localDateFin);
        return (double) (duree * reservation.getVehicule().getTarif_de_location());
    }

    @Override
    public List<Facture> getFacturesByClientId(Long clientId) {
        return factureRepository.findByReservation_Client_Id(clientId);
    }

    @Override
    public List<Facture> getFacturesByPeriode(Date debut, Date fin) {
        return factureRepository.findByDateFacturationBetween(debut, fin);
    }

    @Override
    public byte[] genererFacturePdf(Long id) {
        Facture facture = factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture introuvable"));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            /*Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            // Ajouter du contenu au PDF
            document.add(new Paragraph("Facture #" + facture.getIdFacture()));
            document.add(new Paragraph("Date de Facturation : " + facture.getDateFacturation()));
            document.add(new Paragraph("Montant Total : " + facture.getMontantTotal() + " €"));
            document.add(new Paragraph("Statut : " + facture.getStatut().toString()));

            document.close();*/
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

}
