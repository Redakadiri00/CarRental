package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.Models.Facture;
import java.util.List;

public interface FactureServiceInterface {

    public List<Facture> getAllFactures();
    public Facture getFactureById(Long id);
    public Facture saveFacture(Facture facture);
    public void deleteFacture(Long id);
    public Facture updateFacture(Long id, Facture facture);
}
