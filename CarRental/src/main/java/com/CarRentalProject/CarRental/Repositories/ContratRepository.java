package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findByFactureReservationClientId(Long clientId);

    List<Contrat> findByDateCreationBetween(Date debut, Date fin);

    Optional<Contrat> findByFacture_IdFacture(Long factureId);
}
