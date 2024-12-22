package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
}
