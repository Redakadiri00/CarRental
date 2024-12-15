package com.CarRentalProject.CarRental.Models;

public enum Caracteristique_voiture {
    Auto,
    Manuel,
    Petrol,
    Hybrid,
    Electric,
    Essence,
    Diesel,
    climatisation,
    gps,
    quatre_passagers("4places"),
    deux_passagers("2places");

    private final String description;

    Caracteristique_voiture(String description) {
        this.description = description;
    }

    Caracteristique_voiture(){
        this.description = name();
    }

    public String getDescription() {
        return description;
    }

}
