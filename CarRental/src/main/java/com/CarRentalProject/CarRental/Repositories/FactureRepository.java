package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.Models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByReservation_Client_Id(Long clientId);

    List<Facture> findByDateFacturationBetween(Date debut, Date fin);
}