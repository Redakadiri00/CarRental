package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Controllers.VehiculeController;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Repositories.VehiculeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehiculeService implements VehiculeServiceInterface {

    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {

        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public Vehicule addVehicule(Vehicule vehicule) {
        boolean exists = vehiculeRepository.existsByMarqueAndModelAndType(
                vehicule.getMarque(), vehicule.getModel(), vehicule.getType());
        if (exists) {
            throw new IllegalStateException("Vehicule already exists");
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }


    public void deleteVehicule(Integer id) {
        vehiculeRepository.deleteById(id);
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public List<String> getModelByMarque(String marque){
        return vehiculeRepository.getModelByMarque(marque);
    }

    public List<String> getMarque(){
        return vehiculeRepository.getMarqueOfVehicules();
    }

    public List<String> getModelOfMarque(String marque){
        return vehiculeRepository.getModelByMarque(marque);
    }


    @Override
    public List<Vehicule> getVehiculeByTarifBetween(int tarifmin, int tarifmax) {
        return vehiculeRepository.findByTarifBetween(tarifmin, tarifmax);
    }

//    @Override
//   public  List<Vehicule> getVehiculeByDateEntre(LocalDate dateDebut, LocalDate dateFin){
//        return VehiculeRepository.findAvailableVehicules(dateDebut, dateFin);
//    }


    @Override
    public Vehicule getVehiculeById(Integer id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicule not found with ID: " + id));
    }

    @Override
    public List<Vehicule> getVehiculeByType(String type) {
        return List.of();
    }

    @Override
    public List<Vehicule> getVehiculeByMarque(String marque) {
        return vehiculeRepository.findByMarque(marque);
    }

    @Override
    public List<Vehicule> getVehiculeByModel(String model) {
        return List.of();
    }

    @Override
    public Vehicule searchVehicule(String marque, String model, String type) {
        return null;
    }

    @Override
    public List<Vehicule> getVehiculeByCaracteristique(String caracteristique) {
        return List.of();
    }

    @Override
    public List<Vehicule> getVehiculeBystatus_voiture(String status) {
        return List.of();
    }


    @Override
    public List<Vehicule> getVehiculeByMarqueAndModel(String marque, String model) {
        return List.of();
    }

    @Override
    public List<Vehicule> getVehiculeByMarqueAndModelAndTarif(String marque, String model, int tarifmin, int tarifmax) {
        return List.of();
    }
}
