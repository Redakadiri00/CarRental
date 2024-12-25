package com.CarRentalProject.CarRental.Models;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.CarRentalProject.CarRental.Enums.UserStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
