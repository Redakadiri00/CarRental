package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findByFactureReservationClientId(Long clientId);

    List<Contrat> findByDateCreationBetween(Date debut, Date fin);
}
