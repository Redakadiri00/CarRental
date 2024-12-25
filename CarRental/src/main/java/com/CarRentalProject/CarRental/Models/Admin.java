package com.CarRentalProject.CarRental.Models;

import com.CarRentalProject.CarRental.Enums.AdminLevel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "admin_level", nullable = false)
    private AdminLevel adminLevel;
}
