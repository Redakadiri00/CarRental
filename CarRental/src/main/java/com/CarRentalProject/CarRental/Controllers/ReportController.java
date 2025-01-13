// ReportController.java
package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.DTO.AggregatedReportDTO;
import com.CarRentalProject.CarRental.DTO.ReportDTO;
import com.CarRentalProject.CarRental.Services.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    // Existing endpoints...

    @GetMapping("/weekly/aggregated")
    public ResponseEntity<AggregatedReportDTO> getAggregatedWeeklyReport(@RequestParam(defaultValue = "4") int weeks) {
        AggregatedReportDTO aggregatedWeeklyReport = reportService.generateAggregatedWeeklyReport(weeks);
        return ResponseEntity.ok(aggregatedWeeklyReport);
    }

    @GetMapping("/monthly/aggregated")
    public ResponseEntity<AggregatedReportDTO> getAggregatedMonthlyReport(@RequestParam(defaultValue = "6") int months) {
        AggregatedReportDTO aggregatedMonthlyReport = reportService.generateAggregatedMonthlyReport(months);
        return ResponseEntity.ok(aggregatedMonthlyReport);
    }
}
