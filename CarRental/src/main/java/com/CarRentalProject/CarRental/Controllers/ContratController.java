package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.Models.Contrat;
import com.CarRentalProject.CarRental.Services.ContratServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrats")
public class ContratController {

    @Autowired
    private ContratServiceInterface contratService;

    @GetMapping
    public List<Contrat> getAllContrats() {
        return contratService.getAllContrats();
    }

    @GetMapping("/{id}")
    public Contrat getContratById(@PathVariable Long id) {
        return contratService.getContratById(id);
    }

    @PostMapping
    public Contrat saveContrat(@RequestBody Contrat contrat) {
        return contratService.saveContrat(contrat);
    }

    @DeleteMapping("/{id}")
    public void deleteContrat(@PathVariable Long id) {
        contratService.deleteContrat(id);
    }

    @PutMapping("/{id}")
    public Contrat updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        return contratService.updateContrat(id, contrat);
    }
}

