package com.CarRentalProject.CarRental.DTO;

import java.time.LocalDate;
import java.util.Map;

public class ReportDTO {
    private String period; // e.g., "Weekly", "Monthly"
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalReservations;
    private double totalRevenue;
    private Map<String, Integer> reservationsByVehicleType;
    private Map<String, Double> revenueByVehicleType; // Optional

    // Constructors
    public ReportDTO() {}

    public ReportDTO(String period, LocalDate startDate, LocalDate endDate, int totalReservations, double totalRevenue, Map<String, Integer> reservationsByVehicleType, Map<String, Double> revenueByVehicleType) {
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalReservations = totalReservations;
        this.totalRevenue = totalRevenue;
        this.reservationsByVehicleType = reservationsByVehicleType;
        this.revenueByVehicleType = revenueByVehicleType;
    }


    // Getters and Setters
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(int totalReservations) {
        this.totalReservations = totalReservations;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Map<String, Integer> getReservationsByVehicleType() {
        return reservationsByVehicleType;
    }

    public void setReservationsByVehicleType(Map<String, Integer> reservationsByVehicleType) {
        this.reservationsByVehicleType = reservationsByVehicleType;
    }

    public void setStartDate(LocalDate monthStart) {
        this.startDate = monthStart;
    }

    public void setEndDate(LocalDate monthEnd) {
        this.endDate = monthEnd;
    }

    public void setRevenueByVehicleType(Map<String, Double> revenueByVehicleType) {
        this.revenueByVehicleType = revenueByVehicleType;
    }

    public LocalDate getStartDate() {

        return startDate;

    }
}
