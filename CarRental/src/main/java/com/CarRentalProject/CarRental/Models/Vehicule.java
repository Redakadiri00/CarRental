package com.CarRentalProject.CarRental.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicule")
public class Vehicule {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id_voiture;
    private String marque;
    private String model;
    private String type;
    private int tarifDeLocation;
    private String status;
    private String imageVoiture;
    private Enum Caracteristique_voiture;

    public Vehicule(Integer id_voiture, String marque, String model, String type, int tarifDeLocation, String status, String imageVoiture, Enum Caracteristique_voiture) {
        this.id_voiture = id_voiture;
        this.marque = marque;
        this.model = model;
        this.type = type;
        this.tarifDeLocation = tarifDeLocation;
        this.status = status;
        this.imageVoiture = imageVoiture;
        this.Caracteristique_voiture = Caracteristique_voiture;
    }

    public Vehicule(){

    }

    public Enum getCaracteristique_voiture() {
        return Caracteristique_voiture;
    }

    public String getImageVoiture() {
        return imageVoiture;
    }

    public String getStatus() {
        return status;
    }

    public int getTarif_de_Location() {
        return tarifDeLocation;
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
        return id_voiture;
    }

    public void setId_voiture(int id) {
        this.id_voiture = id_voiture;
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

    public void setTarif_de_Location(int tarifDeLocation) {
        this.tarifDeLocation = tarifDeLocation;
    }

    public void setStatus(String status) {
        status = status;
    }

    public void setImageVoiture(String imageVoiture) {
        this.imageVoiture = imageVoiture;
    }

    public void setCaracteristique_voiture(Enum caracteristique_voiture) {
        Caracteristique_voiture = caracteristique_voiture;
    }
}