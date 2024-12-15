package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Caracteristique_voiture;
import com.CarRentalProject.CarRental.Models.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {

    List<Vehicule> findByType(String type);
    List<Vehicule> findByMarque(String marque);
    List<Vehicule> findByModel(String model);
    Vehicule findByMarqueAndModelAndType(String marque, String model, String type);
    List<Vehicule> findByCaracteristique(Caracteristique_voiture caracteristique);
    List<Vehicule> findByStatus(String status);
    List<Vehicule> findByTarifDeLocationBetween(int tarifmin, int tarifmax);
    List<Vehicule> findByMarqueAndModel(String marque, String model);
    List<Vehicule> findByMarqueAndModelAndTarifDeLocationBetween(String marque, String model, int tarifmin, int tarifmax);
}
