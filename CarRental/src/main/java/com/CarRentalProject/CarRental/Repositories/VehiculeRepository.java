package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    public void deleteById(Integer id);

    List<Vehicule> findByType(String type);
    List<Vehicule> findByMarque(String marque);
    List<Vehicule> findByModel(String model);

    Vehicule findByMarqueAndModelAndType(String marque, String model, String type);
    //List<Vehicule> findByCaracteristique(Caracteristique_voiture caracteristique);
    //List<Vehicule> findBystatus_voiture(String status);
    //List<Vehicule> findBytarif_de_locationBetween(int tarifmin, int tarifmax);
    //List<Vehicule> findByTarif_de_location(int tarif);
    List<Vehicule> findByTarifBetween(int tarifmin, int tarifmax);
    List<Vehicule> findByMarqueAndModel(String marque, String model);
    //List<Vehicule> findByMarqueAndModelAndTarif_de_location(String marque, String model, int tarifmin, int tarifmax);

    @Query("SELECT v.model FROM Vehicule v WHERE v.marque = :marque")
    List<String> getModelByMarque(@Param("marque") String marque);
    boolean existsByMarqueAndModelAndType(String marque, String model, String type);

    @Query("SELECT v FROM Vehicule v WHERE v.Id NOT IN " +
            "(SELECT r.vehicule.Id FROM Reservation r WHERE " +
            "(r.dateDebut <= :dateFin AND r.dateFin >= :dateDebut))")
    List<Vehicule> findAvailableVehicules(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

}
