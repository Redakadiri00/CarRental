package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Vehicule;

import java.time.LocalDate;
import java.util.List;

public interface VehiculeServiceInterface {

    Vehicule addVehicule(Vehicule vehicule);
    Vehicule updateVehicule(Vehicule vehicule);
    public void deleteVehicule(Integer id);
    List<Vehicule> getAllVehicules();
    Vehicule getVehiculeById(Integer id);
    List<Vehicule> getVehiculeByType(String type);
    List<Vehicule> getVehiculeByMarque(String marque);
    List<Vehicule> getVehiculeByModel(String model);
    Vehicule searchVehicule(String marque, String model, String type);

    List<String> getModelByMarque(String marque);

    //List <Vehicule> getVehiculeByDateEntre(LocalDate dateDebut, LocalDate dateFin);


    List<Vehicule> getVehiculeByCaracteristique(String caracteristique);
    List<Vehicule> getVehiculeBystatus_voiture(String status);
    List<Vehicule> getVehiculeByTarifBetween(int tarifmin, int Tarifmax);
    List<Vehicule> getVehiculeByMarqueAndModel(String marque, String model);
    List<Vehicule> getVehiculeByMarqueAndModelAndTarif(String marque,String model, int tarifmin, int tarifmax);








}
