package com.CarRentalProject.CarRental.Models.UserModels;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

import com.CarRentalProject.CarRental.Enums.PaymentMethod;

/**
 * Represents a client in the car rental system.
 * Extends the User class and includes additional attributes specific to clients.
 * 
 * <p>Attributes:</p>
 * <ul>
 *   <li>driversLicense: The client's driver's license number, must be 10 characters long and alphanumeric.</li>
 *   <li>licenseExpiry: The expiry date of the client's driver's license.</li>
 *   <li>preferredPaymentMethod: The client's preferred payment method.</li>
 *   <li>membershipPoints: The client's membership points, default is 0.</li>
 *   <li>verifiedDriver: Indicates if the client is a verified driver, default is false.</li>
 *   <li>notes: Additional notes about the client, up to 500 characters.</li>
 * </ul>
 * 
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Data: Generates getters, setters, toString, equals, and hashCode methods.</li>
 *   <li>@EqualsAndHashCode(callSuper = true): Includes superclass properties in equals and hashCode methods.</li>
 *   <li>@Entity: Specifies that this class is an entity and is mapped to a database table.</li>
 *   <li>@DiscriminatorValue("CLIENT"): Specifies the value used for the entity type in the single table inheritance.</li>
 *   <li>@NotNull: Ensures the annotated field is not null.</li>
 *   <li>@Pattern: Ensures the annotated field matches the specified regular expression.</li>
 *   <li>@Column: Specifies the details of the column to which a field or property will be mapped.</li>
 *   <li>@Enumerated(EnumType.STRING): Specifies that the enum value should be persisted as a string.</li>
 *   <li>@Size: Ensures the annotated field's size is within the specified bounds.</li>
 * </ul>
 */

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
