package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Services.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @Autowired
    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }
    //dsdsadsa

    @PostMapping
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.addVehicule(vehicule);
    }

    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }

   // not working
    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@PathVariable Integer id){
        return vehiculeService.getVehiculeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicule(@PathVariable int id) {
        vehiculeService.deleteVehicule(id);
    }

    @PutMapping
    public Vehicule updateVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.updateVehicule(vehicule);
    }

    @GetMapping("/marque/{marque}")
    public List<String> getModelByMarque(@PathVariable("marque") String marque) {
        return vehiculeService.getModelByMarque(marque);
    }

    @GetMapping("/tarif/{tarifmin}-{tarifmax}")
    public List<Vehicule> getVehiculeByTarifBetween(@PathVariable("tarifmin") int tarifmin, @PathVariable("tarifmax") int tarifmax) {
        return vehiculeService.getVehiculeByTarifBetween(tarifmin, tarifmax);
    }


}
