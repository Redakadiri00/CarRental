package com.CarRentalProject.CarRental.Models.UserModels;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.CarRentalProject.CarRental.Enums.UserStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Represents a user in the Car Rental system.
 * This class is annotated as an entity and mapped to the "users" table in the database.
 * It includes various fields such as name, username, address, phone, birthdate, registration date, last updated date, status, email, and password.
 * The class uses Lombok annotations for boilerplate code reduction and Hibernate annotations for ORM mapping.
 * 
 * Annotations:
 * - @Data: Lombok annotation to generate getters, setters, toString, equals, and hashCode methods.
 * - @Builder: Lombok annotation to implement the builder pattern.
 * - @AllArgsConstructor: Lombok annotation to generate a constructor with all fields.
 * - @NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
 * - @Entity: Specifies that the class is an entity and is mapped to a database table.
 * - @Table: Specifies the primary table for the annotated entity and indexes on email and phone columns.
 * - @Inheritance: Specifies the inheritance strategy for the entity.
 * - @DiscriminatorColumn: Specifies the column used for the discriminator value in the inheritance hierarchy.
 * - @Id: Specifies the primary key of the entity.
 * - @GeneratedValue: Provides the specification of generation strategies for the primary keys.
 * - @NotNull: Specifies that a field cannot be null.
 * - @Size: Specifies the size constraints for a field.
 * - @Pattern: Specifies the regular expression that a field must match.
 * - @CreationTimestamp: Specifies that the field should be populated with the current timestamp when the entity is created.
 * - @UpdateTimestamp: Specifies that the field should be populated with the current timestamp when the entity is updated.
 * - @Enumerated: Specifies that a persistent property or field should be persisted as an enumerated type.
 * - @Email: Specifies that a field must be a valid email address.
 * 
 * Fields:
 * - id: The unique identifier for the user.
 * - name: The name of the user.
 * - username: The username of the user.
 * - address: The address of the user.
 * - phone: The phone number of the user.
 * - birthdate: The birthdate of the user.
 * - registrationDate: The date when the user registered.
 * - lastUpdatedDate: The date when the user's information was last updated.
 * - status: The status of the user.
 * - email: The email address of the user.
 * - password: The password of the user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_phone", columnList = "phone")
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @NotNull
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(name = "phone", nullable = false, unique = true, length = 50)
    private String phone;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @CreationTimestamp
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDate registrationDate;

    @UpdateTimestamp
    @Column(name = "last_updated_date", nullable = false)
    private LocalDate lastUpdatedDate;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private UserStatus status;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull @Size(min = 1, max = 50) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 1, max = 50) String name) {
        this.name = name;
    }

    public @NotNull @Size(min = 5, max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(min = 5, max = 50) String username) {
        this.username = username;
    }

    public @NotNull @Size(min = 1, max = 100) String getAddress() {
        return address;
    }

    public void setAddress(@NotNull @Size(min = 1, max = 100) String address) {
        this.address = address;
    }

    public @NotNull @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String phone) {
        this.phone = phone;
    }

    public @NotNull LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(@NotNull LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public @NotNull UserStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull UserStatus status) {
        this.status = status;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull @Size(min = 8, max = 100) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = 8, max = 100) String password) {
        this.password = password;
    }

    @NotNull
    @Size(min = 8, max = 100)
    @Column(name = "password", nullable = false)
    private String password;
}
