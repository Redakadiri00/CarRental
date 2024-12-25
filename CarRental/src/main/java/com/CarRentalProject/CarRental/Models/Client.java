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

    public @NotNull @Pattern(regexp = "^[A-Z0-9]{10}$", message = "Driver's license must be 10 characters") String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(@NotNull @Pattern(regexp = "^[A-Z0-9]{10}$", message = "Driver's license must be 10 characters") String driversLicense) {
        this.driversLicense = driversLicense;
    }

    public @NotNull LocalDate getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setLicenseExpiry(@NotNull LocalDate licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public PaymentMethod getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(PaymentMethod preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public Integer getMembershipPoints() {
        return membershipPoints;
    }

    public void setMembershipPoints(Integer membershipPoints) {
        this.membershipPoints = membershipPoints;
    }

    public Boolean getVerifiedDriver() {
        return verifiedDriver;
    }

    public void setVerifiedDriver(Boolean verifiedDriver) {
        this.verifiedDriver = verifiedDriver;
    }

    public @Size(max = 500) String getNotes() {
        return notes;
    }

    public void setNotes(@Size(max = 500) String notes) {
        this.notes = notes;
    }
}
