package com.CarRentalProject.CarRental.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicule")
public class Vehicule {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String marque;
    private String model;
    private String type;
    private int tarif_de_location;

    @JsonProperty("status")
    private String status_voiture;
    private String imageVoiture;

    @Enumerated(EnumType.STRING)
    @JsonProperty("caracteristique")
    private Caracteristique_voiture caracteristique;

    public Vehicule(Integer Id, String marque, String model, String type, int tarif_de_location, String status_voiture, String imageVoiture, Caracteristique_voiture caracteristique) {
        this.Id = Id;
        this.marque = marque;
        this.model = model;
        this.type = type;
        this.tarif_de_location = tarif_de_location;
        this.status_voiture = status_voiture;
        this.imageVoiture = imageVoiture;
        this.caracteristique = caracteristique;
    }

    public Vehicule(){

    }

    public Caracteristique_voiture getcaracteristique() {
        return caracteristique;
    }

    public String getImageVoiture() {
        return imageVoiture;
    }

    public String getStatus_voiture() {
        return status_voiture;
    }

    public int gettarif_de_location() {
        return tarif_de_location;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public String getMarque() {
        return marque;
    }

    public int getId_voiture() {
        return Id;
    }

    public void setId_voiture(int id) {
        this.Id = Id;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void settarif_de_location(int tarif_de_location) {
        this.tarif_de_location = tarif_de_location;
    }

    public void setStatus_voiture(String status_voiture) {
        this.status_voiture = status_voiture;
    }

    public void setImageVoiture(String imageVoiture) {
        this.imageVoiture = imageVoiture;
    }

    public void setCaracteristique_voiture(Caracteristique_voiture caracteristique) {
        caracteristique = caracteristique;
    }
}