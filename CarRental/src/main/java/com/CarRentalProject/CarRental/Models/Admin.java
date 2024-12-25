package com.CarRentalProject.CarRental.Models;

import jakarta.persistence.*;

@Entity
public class Admin extends User {

    public Admin() {
    }

    public Admin(String name, String surname, String adress, String phone, String birthdate, String status) {
        super();
    }

}
