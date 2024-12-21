package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
}