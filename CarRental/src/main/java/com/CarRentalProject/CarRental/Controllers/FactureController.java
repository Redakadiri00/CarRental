package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.DTO.FactureDTO;
import com.CarRentalProject.CarRental.DTO.ReservationDTO;
import com.CarRentalProject.CarRental.Models.Facture;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Services.FactureServiceInterface;
import com.CarRentalProject.CarRental.Services.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @GetMapping
    public List<FactureDTO> getAllFactures() {
        System.out.println("Requête reçue pour getAllFactures");
        return factureService.getAllFactures();
    }

    @GetMapping("/{id}")
    public FactureDTO getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id);
    }

    @PostMapping
    public FactureDTO creerFactureAvecMontant(@RequestBody ReservationDTO reservation) {
        return factureService.creerFactureAvecMontant(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<FactureDTO> updateFacture(@PathVariable Long id, @RequestBody FactureDTO factureDTO) {
        System.out.println("Requête reçue pour updateFacture avec ID : " + id);
        FactureDTO updatedFacture = factureService.updateFacture(id, factureDTO);
        System.out.println("Facture mise à jour avec succès pour ID : " + id);
        return ResponseEntity.ok(updatedFacture);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> telechargerFacturePdf(@PathVariable Long id) {
        byte[] pdf = factureService.genererFacturePdf(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
