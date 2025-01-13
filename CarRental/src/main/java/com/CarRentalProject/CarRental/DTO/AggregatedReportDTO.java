package com.CarRentalProject.CarRental.DTO;

import java.util.List;

public class AggregatedReportDTO {
    private String periodType; // "Weekly" or "Monthly"
    private List<ReportDTO> reports;

    // Constructors
    public AggregatedReportDTO() {}

    public AggregatedReportDTO(String periodType, List<ReportDTO> reports) {
        this.periodType = periodType;
        this.reports = reports;
    }

    // Getters and Setters
    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public List<ReportDTO> getReports() {
        return reports;
    }

    public void setReports(List<ReportDTO> reports) {
        this.reports = reports;
    }
}
