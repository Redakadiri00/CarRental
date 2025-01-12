package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.DTO.FactureDTO;
import com.CarRentalProject.CarRental.DTO.ReservationDTO;
import com.CarRentalProject.CarRental.Enums.StatutFacture;
import com.CarRentalProject.CarRental.Enums.ModePaiement;
import com.CarRentalProject.CarRental.Mappers.FactureMapper;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Repositories.FactureRepository;
import com.CarRentalProject.CarRental.Repositories.ReservationRepository;
import com.CarRentalProject.CarRental.Repositories.VehiculeRepository;
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
        System.out.println("Recherche de la facture avec ID : " + id);
        Facture existingFacture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        existingFacture.setMontantTotal(factureDTO.getMontantTotal());
        existingFacture.setStatut(StatutFacture.valueOf(factureDTO.getStatut()));
        existingFacture.setDateFacturation(new Date());

        Facture updatedFacture = factureRepository.save(existingFacture);
        System.out.println("Facture mise à jour : " + updatedFacture);
        return factureMapper.toDTO(updatedFacture);
    }




   @Override
    public FactureDTO creerFactureAvecMontant(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(reservationDTO.getVehiculeId())
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        double montantTotal = calculerMontantTotal(reservationDTO);

        Facture facture = new Facture();
        facture.setDateFacturation(new Date());
        facture.setMontantTotal(montantTotal);
        facture.setStatut(StatutFacture.NON_PAYEE);
        facture.setModePaiement(ModePaiement.CB);
        facture.setReservation(reservation);

        Facture savedFacture = factureRepository.save(facture);
        contratService.genererContrat(savedFacture);

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
    public Facture marquerCommePayee(Long id) {
        Facture facture = factureRepository.findById(id).orElse(null);
        facture.setStatut(StatutFacture.PAYEE);
        return factureRepository.save(facture);
    }

//    @Override
//    public Double calculerMontantTotal(ReservationDTO reservation) {
//        Vehicule vehicule = vehiculeRepository.findById(reservation.getVehiculeId())
//                .orElseThrow(() -> new IllegalArgumentException("Vehicule not found"));
//
//        LocalDate localDateDebut = reservation.getDateDebut();
//        LocalDate localDateFin = reservation.getDateFin();
//
//        long duree = ChronoUnit.DAYS.between(localDateDebut, localDateFin);
//        double montantTotal = duree * vehicule.getTarif_de_location();
//        System.out.println("Montant total : " + montantTotal);
//        return montantTotal;
//    }





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
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            document.add(new Paragraph("Facture #" + facture.getIdFacture()));
            document.add(new Paragraph("Date de Facturation : " + facture.getDateFacturation()));
            document.add(new Paragraph("Montant Total : " + facture.getMontantTotal() + " €"));
            document.add(new Paragraph("Statut : " + facture.getStatut()));

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

}
