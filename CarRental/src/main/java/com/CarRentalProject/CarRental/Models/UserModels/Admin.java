package com.CarRentalProject.CarRental.Models.UserModels;

import com.CarRentalProject.CarRental.Enums.AdminLevel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents an Admin user in the Car Rental system.
 * This class extends the User class and includes additional attributes specific to Admin users.
 * 
 * <p>The Admin class is annotated with JPA annotations to map it to a database table.
 * It uses the @DiscriminatorValue annotation to specify the discriminator value for Admin users in a single table inheritance strategy.</p>
 * 
 * <p>Attributes:</p>
 * <ul>
 *   <li>{@code adminLevel} - The level of the admin, represented by the {@link AdminLevel} enum. This field is mandatory and stored as a string in the database.</li>
 * </ul>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #getAdminLevel()} - Returns the admin level of the user.</li>
 *   <li>{@link #setAdminLevel(AdminLevel)} - Sets the admin level of the user.</li>
 * </ul>
 * 
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Data} - Lombok annotation to generate getters, setters, toString, equals, and hashCode methods.</li>
 *   <li>{@code @EqualsAndHashCode(callSuper = true)} - Lombok annotation to include superclass fields in equals and hashCode methods.</li>
 *   <li>{@code @Entity} - JPA annotation to mark this class as a persistent entity.</li>
 *   <li>{@code @DiscriminatorValue("ADMIN")} - JPA annotation to specify the discriminator value for this entity in a single table inheritance strategy.</li>
 *   <li>{@code @NotNull} - Bean Validation annotation to ensure the field is not null.</li>
 *   <li>{@code @Enumerated(EnumType.STRING)} - JPA annotation to specify that the enum should be persisted as a string.</li>
 *   <li>{@code @Column(name = "admin_level", nullable = false)} - JPA annotation to specify the column details for the adminLevel field.</li>
 * </ul>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin")
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "admin_level", nullable = false)
    private AdminLevel adminLevel;

    /**
     * Returns the admin level of the user.
     * @return The admin level of the user.
     */
    public AdminLevel getAdminLevel() {
        return adminLevel;
    }

    /**
     * Sets the admin level of the user.
     * @param adminLevel The admin level to set.
     */
    public void setAdminLevel(@NotNull AdminLevel adminLevel) {
        this.adminLevel = adminLevel;
    }
}
