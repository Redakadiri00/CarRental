package com.CarRentalProject.CarRental.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;

@Entity
//@Table(name = "vehicule")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String marque;
    private String model;
    private String type;
    private int tarif;

    @JsonProperty("status")
    private String status_voiture;
    private String imageVoiture;

    @ElementCollection(targetClass = Caracteristique_voiture.class)
    @Enumerated(EnumType.STRING)
   /* @CollectionTable(name = "vehicule_caracteristiques")
    @Column(name = "caracteristique")*/
    @JsonProperty("caracteristique")
    private Set<Caracteristique_voiture> caracteristique;

    public Vehicule(Integer Id, String marque, String model, String type, int tarif, String status_voiture, String imageVoiture, Set<Caracteristique_voiture> caracteristique) {
        this.id = Id;
        this.marque = marque;
        this.model = model;
        this.type = type;
        this.tarif = tarif;
        this.status_voiture = status_voiture;
        this.imageVoiture = imageVoiture;
        this.caracteristique = caracteristique;
    }

    public Vehicule() {
    }

    public Set<Caracteristique_voiture> getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(Set<Caracteristique_voiture> caracteristique) {
        this.caracteristique = caracteristique;
    }

    public String getImageVoiture() {
        return imageVoiture;
    }

    public void setImageVoiture(String imageVoiture) {
        this.imageVoiture = imageVoiture;
    }

    public String getStatus_voiture() {
        return status_voiture;
    }

    public void setStatus_voiture(String status_voiture) {
        this.status_voiture = status_voiture;
    }

    public int getTarif_de_location() {
        return tarif;
    }

    public void setTarif_de_location(int TarifDeLocation) {
        this.tarif = TarifDeLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Integer getId_voiture() {
        return id;
    }

    public void setId_voiture(Integer Id) {
        this.id = Id;
    }
}