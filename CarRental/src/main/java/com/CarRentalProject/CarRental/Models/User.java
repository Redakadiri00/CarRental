package com.CarRentalProject.CarRental.Models;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "adress", nullable = false, length = 50)
    private String adress;

    @Column(name = "phone", nullable = false, length = 50, unique = true)
    private String phone;

    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    @Column(name = "registration_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date registrationDate;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    // Constructors
    public User() {
    }

    public User(String name, String username, String adress, String phone, Date birthdate, String status, String email,
            String password) {
        this.name = name;
        this.username = username;
        this.adress = adress;
        this.phone = phone;
        this.birthdate = birthdate;
        this.status = status;
        this.email = email;
        this.password = password;
    }
}
