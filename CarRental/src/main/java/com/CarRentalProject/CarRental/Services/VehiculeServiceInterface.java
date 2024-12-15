package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Vehicule;

import java.util.List;

public interface VehiculeServiceInterface {

    Vehicule addVehicule(Vehicule vehicule);
    Vehicule updateVehicule(Vehicule vehicule);
    void deleteVehicule(Vehicule vehicule);
    List<Vehicule> getAllVehicules();
    Vehicule getVehiculeById(int id);
    List<Vehicule> getVehiculeByType(String type);
    List<Vehicule> getVehiculeByMarque(String marque);
    List<Vehicule> getVehiculeByModel(String model);
    Vehicule searchVehicule(String marque, String model, String type);



    List<Vehicule> getVehiculeByCaracteristique(String caracteristique);
    List<Vehicule> getVehiculeByStatus(String status);
    List<Vehicule> getVehiculeByTarif(int tarifmin, int Tarifmax);
    List<Vehicule> getVehiculeByMarqueAndModel(String marque, String model);
    List<Vehicule> getVehiculeByMarqueAndModelAndTarif(String marque,String model, int tarifmin, int tarifmax);







}
