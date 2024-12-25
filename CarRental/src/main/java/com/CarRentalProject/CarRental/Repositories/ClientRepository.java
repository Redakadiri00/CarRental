package com.CarRentalProject.CarRental.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CarRentalProject.CarRental.Models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByDriversLicense(String license);
    List<Client> findByVerifiedDriver(Boolean verified);
    List<Client> findByMembershipPointsGreaterThan(Integer points);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByUsername(String username);
}
