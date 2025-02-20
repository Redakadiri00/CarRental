package com.CarRentalProject.CarRental.Repositories;

import com.CarRentalProject.CarRental.DTO.DateRangeDTO;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Models.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.vehicule = :vehicule AND " +
            "(r.dateDebut <= :dateFin AND r.dateFin >= :dateDebut)")
    List<Reservation> findReservationsByVehiculeAndDateRange(@Param("vehicule") Vehicule vehicule,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);

    @Query("SELECT new com.CarRentalProject.CarRental.DTO.DateRangeDTO(r.dateDebut, r.dateFin) FROM Reservation r WHERE r.vehicule.Id = :vehiculeId")
    List<DateRangeDTO> findReservedDatesByVehiculeId(@Param("vehiculeId") Integer vehiculeId);

    @Query("SELECT r FROM Reservation r WHERE r.dateDebut >= :startDate AND r.dateDebut <= :endDate")
    List<Reservation> findReservationsByDateRange(LocalDate startDate, LocalDate endDate);
}
