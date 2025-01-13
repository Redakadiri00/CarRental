package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.DTO.FactureDTO;
import com.CarRentalProject.CarRental.DTO.ReservationDTO;
import com.CarRentalProject.CarRental.Enums.StatutFacture;
import com.CarRentalProject.CarRental.Enums.ModePaiement;
import com.CarRentalProject.CarRental.Mappers.FactureMapper;
import com.CarRentalProject.CarRental.Mappers.ReservationMapper;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.UserModels.Client;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Repositories.FactureRepository;
import com.CarRentalProject.CarRental.Repositories.ReservationRepository;
import com.CarRentalProject.CarRental.Repositories.VehiculeRepository;
import com.CarRentalProject.CarRental.Services.UserServices.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
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



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service // Déclare la classe comme un bean de service Spring
public class FactureService implements FactureServiceInterface {

    @Autowired // Injecte le bean FactureRepository ici pour pouvoir l'utiliser dans cette classe (injection de dépendance)
    private FactureRepository factureRepository;

    @Autowired
    private ContratService contratService;

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private FactureMapper factureMapper;

    @Autowired
    private  ReservationMapper reservationMapper;

    @Autowired
    private ClientService ClientService;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    @Override
    public List<FactureDTO> getAllFactures() {
        return factureRepository.findAll()
                .stream()
                .map(factureMapper::toDTO)
                .toList();
    }

    @Override
    public FactureDTO getFactureById(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));
        return factureMapper.toDTO(facture);
    }
    @Override
    public FactureDTO saveFacture(FactureDTO factureDTO) {
        Reservation reservation = reservationRepository.findById(factureDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
        Facture facture = factureMapper.fromDTO(factureDTO, reservation);
        Facture savedFacture = factureRepository.save(facture);
        return factureMapper.toDTO(savedFacture);
    }
    @Override
    public void deleteFacture(Long id) {
        if (!factureRepository.existsById(id)) {
            throw new RuntimeException("Facture introuvable");
        }
        factureRepository.deleteById(id);
    }
    @Override
    public FactureDTO updateFacture(Long id, FactureDTO factureDTO) {
        Facture existingFacture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        existingFacture.setMontantTotal(factureDTO.getMontantTotal());
        existingFacture.setStatut(StatutFacture.valueOf(factureDTO.getStatut()));
        existingFacture.setDateFacturation(new Date());

        Facture updatedFacture = factureRepository.save(existingFacture);
        return factureMapper.toDTO(updatedFacture);
    }




   @Override
    public FactureDTO creerFactureAvecMontant(ReservationDTO reservationDTO) {

        Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

       // Recherche du client
       Client client = ClientService.getClientById(reservationDTO.getClientId());

       // Conversion du DTO en entité
       Reservation reservation = reservationMapper.toEntity(reservationDTO, vehicule, client);
       reservationRepository.save(reservation);

       // Recherche de réservation existante
       List<Reservation> reservationsExistantes = reservationRepository.findReservationsByVehiculeAndDateRange(
               vehicule,
               reservationDTO.getDateDebut(),
               reservationDTO.getDateFin()
       );

       if (reservationsExistantes.isEmpty()) {
              throw new RuntimeException("Aucune réservation existante pour ce véhicule");
       }
        double montantTotal = calculerMontantTotal(reservationDTO);

        Facture facture = new Facture();
        facture.setDateFacturation(new Date());
        facture.setMontantTotal(montantTotal);
        facture.setStatut(StatutFacture.NON_PAYEE);
        facture.setModePaiement(ModePaiement.CB);
        facture.setReservation(reservation);

        Facture savedFacture = factureRepository.save(facture);

        return factureMapper.toDTO(savedFacture);
    }

    @Override
    public Long calculerMontantTotal(ReservationDTO reservationDTO) {
        Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        LocalDate dateDebut = reservationDTO.getDateDebut();
        LocalDate dateFin = reservationDTO.getDateFin();

        long duree = ChronoUnit.DAYS.between(dateDebut, dateFin);
        return duree * vehicule.getTarif_de_location();
    }

    @Override
    @Transactional
    public Facture marquerCommePayee(Long id) {
        Facture facture = factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture introuvable"));
        facture.setStatut(StatutFacture.PAYEE);

        // Rattache l'entité au contexte de persistance avec merge()
        Facture facturePayee = entityManager.merge(facture);

        contratService.genererContrat(facturePayee);
        return facturePayee;
    }




    @Override
    public List<FactureDTO> getFacturesByClientId(Long clientId) {
        return factureRepository.findByReservation_Client_Id(clientId)
                .stream()
                .map(factureMapper::toDTO)
                .toList();
    }

    @Override
    public List<FactureDTO> getFacturesByPeriode(Date debut, Date fin) {
        return factureRepository.findByDateFacturationBetween(debut, fin)
                .stream()
                .map(factureMapper::toDTO)
                .toList();
    }

    @Override
    public byte[] genererFacturePdf(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Titre principal
            document.add(new Paragraph("HaMoRe - Facture")
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // Ligne de séparation
            SolidLine line = new SolidLine();
            line.setLineWidth(1.5f);  // Épaisseur de la ligne
            document.add(new LineSeparator(line));

            // Informations sur la facture
            document.add(new Paragraph("Facture #" + facture.getIdFacture())
                    .setFontSize(18)
                    .setBold()
                    .setMarginTop(10));
            document.add(new Paragraph("Date de facturation : " + facture.getDateFacturation())
                    .setFontSize(12)
                    .setMarginBottom(10));
            document.add(new Paragraph("Montant total : " + facture.getMontantTotal() + " €")
                    .setFontSize(16)
                    .setBold()
                    .setFontColor(ColorConstants.GREEN)
                    .setMarginBottom(10));
            document.add(new Paragraph("Statut : " + facture.getStatut())
                    .setFontSize(14)
                    .setFontColor(facture.getStatut().equals(StatutFacture.PAYEE) ? ColorConstants.GREEN : ColorConstants.RED));

            // Table des détails
            Table table = new Table(2);
            table.addCell(new Cell().add(new Paragraph("Détail").setBold()));
            table.addCell(new Cell().add(new Paragraph("Valeur")));

            table.addCell("ID Facture");
            table.addCell(facture.getIdFacture().toString());

            table.addCell("Montant Total");
            table.addCell(facture.getMontantTotal() + " €");

            document.add(table.setMarginTop(20));

            // Pied de page
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
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }


}
