package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.DTO.ContratDTO;
import com.CarRentalProject.CarRental.Enums.StatutContrat;
import com.CarRentalProject.CarRental.Enums.StatutFacture;
import com.CarRentalProject.CarRental.Mappers.ContratMapper;
import com.CarRentalProject.CarRental.Mappers.FactureMapper;
import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Repositories.ContratRepository;
import com.CarRentalProject.CarRental.Repositories.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.io.ByteArrayOutputStream;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;



import java.util.stream.Collectors;

@Service
public class ContratService implements ContratServiceInterface {

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private ContratMapper contratMapper;
    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private FactureMapper factureMapper;


    @Override
    public List<ContratDTO> getAllContrats() {
        return contratRepository.findAll()
                .stream()
                .map(contratMapper::toDTO) // Convertir chaque Contrat en ContratDTO
                .collect(Collectors.toList());
    }
    @Override
    public ContratDTO getContratById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable avec l'ID : " + id));
        return contratMapper.toDTO(contrat);
    }
    @Override
    public ContratDTO saveContrat(ContratDTO contratDTO) {
        Facture facture = factureRepository.findById(contratDTO.getFactureId())
                .orElseThrow(() -> new RuntimeException("Facture introuvable avec l'ID : " + contratDTO.getFactureId()));
        Contrat contrat = contratMapper.fromDTO(contratDTO, facture);
        contrat.setFacture(facture);
        return contratMapper.toDTO(contratRepository.save(contrat));
    }

    @Override
    public void deleteContrat(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new RuntimeException("Contrat introuvable avec l'ID : " + id);
        }
        contratRepository.deleteById(id);
    }
    @Override
    public ContratDTO updateContrat(Long id, ContratDTO contratDTO) {
        Contrat existingContrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable avec l'ID : " + id));

        Facture facture = factureRepository.findById(contratDTO.getFactureId())
                .orElseThrow(() -> new RuntimeException("Facture introuvable avec l'ID : " + contratDTO.getFactureId()));

        existingContrat.setDateCreation(contratDTO.getDateCreation());
        existingContrat.setFacture(facture);
        existingContrat.setStatut(StatutContrat.valueOf(contratDTO.getStatut()));

        Contrat updatedContrat = contratRepository.save(existingContrat);
        return contratMapper.toDTO(updatedContrat);
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

    @Override
    public ContratDTO genererContrat(Facture facture) {
        if (!facture.getStatut().equals(StatutFacture.PAYEE)) {
                throw new RuntimeException("La facture doit être payée avant de générer un contrat.");
        }

        Contrat contrat = new Contrat();
        contrat.setDateCreation(new Date());
        //facture DTO to facture normal
        contrat.setFacture(facture);
        contrat.setStatut(StatutContrat.EN_COURS);

        Contrat savedContrat = contratRepository.save(contrat);
        return contratMapper.toDTO(savedContrat);
    }
    @Override
    public Contrat validerContrat(Long id) {
        Contrat contrat = contratRepository.findById(id).orElse(null);
        // Validation du contrat
        return contratRepository.save(contrat);
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
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable"));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Titre principal
            document.add(new Paragraph("HaMoRe - Contrat de Location")
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // Ligne de séparation
            SolidLine line = new SolidLine();
            line.setLineWidth(1.5f);
            document.add(new LineSeparator(line));

            // Informations du contrat
            document.add(new Paragraph("Contrat #" + contrat.getIdContrat())
                    .setFontSize(18)
                    .setBold()
                    .setMarginTop(10));
            document.add(new Paragraph("Date de début : " + contrat.getDateCreation())
                    .setFontSize(12)
                    .setMarginBottom(10));
            document.add(new Paragraph("Date de fin : " + contrat.getFacture().getReservation().getDateFin())
                    .setFontSize(12)
                    .setMarginBottom(10));
            document.add(new Paragraph("Montant total : " + contrat.getFacture().getMontantTotal() + " €")
                    .setFontSize(16)
                    .setBold()
                    .setFontColor(ColorConstants.GREEN)
                    .setMarginBottom(10));

            // Table des détails du contrat
            Table table = new Table(2);
            table.addCell(new Cell().add(new Paragraph("Détail").setBold()));
            table.addCell(new Cell().add(new Paragraph("Valeur")));

            table.addCell("ID Contrat");
            table.addCell(contrat.getIdContrat().toString());

            table.addCell("Date de début");
            table.addCell(contrat.getDateCreation().toString());

            table.addCell("Date de fin");
            table.addCell(contrat.getFacture().getReservation().getDateFin().toString());

            table.addCell("Montant Total");
            table.addCell(contrat.getFacture().getMontantTotal() + " €");

            document.add(table.setMarginTop(20));

            // Ajout d'une zone de signature
            document.add(new Paragraph("Signature du client : ")
                    .setMarginTop(30)
                    .setFontSize(14)
                    .setBold());
            document.add(new LineSeparator(line).setMarginBottom(20));  // Ligne pour la signature

            // Footer personnalisé
            document.add(new Paragraph("Merci de votre confiance !")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12)
                    .setMarginTop(30));
            document.add(new Paragraph("www.HaMoRe.com")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.BLUE));

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF du contrat", e);
        }
    }






}
