package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.DTO.ContratDTO;
import com.CarRentalProject.CarRental.Mappers.ContratMapper;
import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Repositories.ContratRepository;
import com.CarRentalProject.CarRental.Services.ContratService;
import com.CarRentalProject.CarRental.Services.ContratServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
public class ContratController {

    @Autowired
    private ContratServiceInterface contratService;

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private ContratMapper contratMapper;

    @GetMapping
    public List<ContratDTO> getAllContrats() {
        return contratService.getAllContrats();
    }

    @GetMapping("/{id}")
    public ContratDTO getContratById(@PathVariable Long id) {
        return contratService.getContratById(id);
    }

    @PostMapping
    public ContratDTO saveContrat(@RequestBody ContratDTO contrat) {
        return contratService.saveContrat(contrat);
    }

    @DeleteMapping("/{id}")
    public void deleteContrat(@PathVariable Long id) {
        contratService.deleteContrat(id);
    }

    @PutMapping("/{id}")
    public ContratDTO updateContrat(@PathVariable Long id, @RequestBody ContratDTO contrat) {
        return contratService.updateContrat(id, contrat);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> telechargerContratPdf(@PathVariable Long id) {
        byte[] pdf = contratService.genererContratPdf(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrat-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/by-facture/{factureId}")
    public ResponseEntity<ContratDTO> getContratByFactureId(@PathVariable Long factureId) {
        Contrat contrat = (Contrat) contratRepository.findByFacture_IdFacture(factureId)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable pour la facture donnée"));
        return ResponseEntity.ok(contratMapper.toDTO(contrat));
    }


}

