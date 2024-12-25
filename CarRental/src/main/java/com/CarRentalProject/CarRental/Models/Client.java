package com.CarRentalProject.CarRental.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

import com.CarRentalProject.CarRental.Enums.PaymentMethod;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    @NotNull
    @Pattern(regexp = "^[A-Z0-9]{10}$", message = "Driver's license must be 10 characters")
    @Column(name = "drivers_license", nullable = false, unique = true)
    private String driversLicense;

    @NotNull
    @Column(name = "license_expiry", nullable = false)
    private LocalDate licenseExpiry;

    @Column(name = "preferred_payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod preferredPaymentMethod;

    @Column(name = "membership_points")
    private Integer membershipPoints = 0;

    @Column(name = "verified_driver")
    private Boolean verifiedDriver = false;

    @Size(max = 500)
    @Column(name = "notes", length = 500)
    private String notes;
}
