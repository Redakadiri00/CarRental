package com.CarRentalProject.CarRental.Services;

import com.CarRentalProject.CarRental.DTO.AggregatedReportDTO;
import com.CarRentalProject.CarRental.DTO.ReportDTO;
import com.CarRentalProject.CarRental.Models.Reservation;
import com.CarRentalProject.CarRental.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

@Service
public class ReportService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReportService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Generates aggregated monthly reports for the past 'numberOfMonths' months.
     *
     * @param numberOfMonths Number of past months to include in the report.
     * @return AggregatedReportDTO containing the monthly reports.
     */
    public AggregatedReportDTO generateAggregatedMonthlyReport(int numberOfMonths) {
        // Input validation
        if (numberOfMonths < 1) {
            throw new IllegalArgumentException("Number of months must be at least 1.");
        }

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(numberOfMonths).with(TemporalAdjusters.firstDayOfMonth());

        // Fetch all confirmed reservations within the date range
        List<Reservation> reservations = reservationRepository.findReservationsByDateRange(startDate, today);

        // Group reservations by YearMonth (e.g., 2023-09)
        Map<YearMonth, List<Reservation>> groupedByMonth = reservations.stream()
                .collect(Collectors.groupingBy(r -> YearMonth.from(r.getDateDebut())));

        List<ReportDTO> reportDTOList = new ArrayList<>();

        for (Map.Entry<YearMonth, List<Reservation>> entry : groupedByMonth.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            List<Reservation> monthReservations = entry.getValue();

            int year = yearMonth.getYear();
            int month = yearMonth.getMonthValue();
            int totalReservations = monthReservations.size();

            // Calculate total revenue: tarif_de_location * number of days between dateDebut and dateFin
            double totalRevenue = monthReservations.stream()
                    .mapToDouble(r -> r.getVehicule().getTarif_de_location() * ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()))
                    .sum();

            // Group by vehicle type for reservations count
            Map<String, Long> reservationsByVehicleType = monthReservations.stream()
                    .collect(Collectors.groupingBy(r -> r.getVehicule().getType(), Collectors.counting()));

            // Group by vehicle type for revenue
            Map<String, Double> revenueByVehicleType = monthReservations.stream()
                    .collect(Collectors.groupingBy(r -> r.getVehicule().getType(),
                            Collectors.summingDouble(r -> r.getVehicule().getTarif_de_location() * ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()))));

            // Calculate start and end dates of the month
            LocalDate monthStart = yearMonth.atDay(1);
            LocalDate monthEnd = yearMonth.atEndOfMonth();

            // Convert reservationsByVehicleType from Map<String, Long> to Map<String, Integer>
            Map<String, Integer> reservationsByVehicleTypeInt = reservationsByVehicleType.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().intValue()
                    ));

            // Create and populate ReportDTO
            ReportDTO report = new ReportDTO();
            report.setPeriod("Monthly");
            report.setStartDate(monthStart);
            report.setEndDate(monthEnd);
            report.setTotalReservations(totalReservations);
            report.setTotalRevenue(totalRevenue);
            report.setReservationsByVehicleType(reservationsByVehicleTypeInt);
            report.setRevenueByVehicleType(revenueByVehicleType);

            reportDTOList.add(report);
        }

        // Sort the reports by start date in ascending order
        reportDTOList.sort(Comparator.comparing(ReportDTO::getStartDate));

        // Create AggregatedReportDTO
        AggregatedReportDTO aggregatedReport = new AggregatedReportDTO("Monthly", reportDTOList);
        return aggregatedReport;
    }

    /**
     * Generates aggregated weekly reports for the past 'numberOfWeeks' weeks.
     *
     * @param numberOfWeeks Number of past weeks to include in the report.
     * @return AggregatedReportDTO containing the weekly reports.
     */
    public AggregatedReportDTO generateAggregatedWeeklyReport(int numberOfWeeks) {
        // Input validation
        if (numberOfWeeks < 1) {
            throw new IllegalArgumentException("Number of weeks must be at least 1.");
        }

        LocalDate today = LocalDate.now();
        // Assuming weeks start on Monday
        LocalDate startDate = today.minusWeeks(numberOfWeeks).with(DayOfWeek.MONDAY);

        // Fetch all confirmed reservations within the date range
        List<Reservation> reservations = reservationRepository.findReservationsByDateRange(startDate, today);

        // Define WeekFields for week-based calculations (locale-specific)
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Group reservations by year and week number
        Map<Integer, Map<Integer, List<Reservation>>> groupedByYearWeek = reservations.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDateDebut().get(weekFields.weekBasedYear()),
                        Collectors.groupingBy(
                                r -> r.getDateDebut().get(weekFields.weekOfWeekBasedYear())
                        )
                ));

        List<ReportDTO> reportDTOList = new ArrayList<>();

        for (Map.Entry<Integer, Map<Integer, List<Reservation>>> yearEntry : groupedByYearWeek.entrySet()) {
            int year = yearEntry.getKey();
            Map<Integer, List<Reservation>> weeksMap = yearEntry.getValue();

            for (Map.Entry<Integer, List<Reservation>> weekEntry : weeksMap.entrySet()) {
                int week = weekEntry.getKey();
                List<Reservation> weekReservations = weekEntry.getValue();

                int totalReservations = weekReservations.size();

                // Calculate total revenue: tarif_de_location * number of days between dateDebut and dateFin
                double totalRevenue = weekReservations.stream()
                        .mapToDouble(r -> r.getVehicule().getTarif_de_location() * ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()))
                        .sum();

                // Group by vehicle type for reservations count
                Map<String, Long> reservationsByVehicleType = weekReservations.stream()
                        .collect(Collectors.groupingBy(r -> r.getVehicule().getType(), Collectors.counting()));

                // Group by vehicle type for revenue
                Map<String, Double> revenueByVehicleType = weekReservations.stream()
                        .collect(Collectors.groupingBy(r -> r.getVehicule().getType(),
                                Collectors.summingDouble(r -> r.getVehicule().getTarif_de_location() * ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()))));

                // Calculate start and end dates of the week
                // Create a temporary date to calculate weekStart based on year and week number
                LocalDate weekStart = LocalDate.now()
                        .withYear(year)
                        .with(weekFields.weekOfWeekBasedYear(), week)
                        .with(weekFields.dayOfWeek(), 1); // Monday

                LocalDate weekEnd = weekStart.plusDays(6);

                // Convert reservationsByVehicleType from Map<String, Long> to Map<String, Integer>
                Map<String, Integer> reservationsByVehicleTypeInt = reservationsByVehicleType.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().intValue()
                        ));

                // Create and populate ReportDTO
                ReportDTO report = new ReportDTO();
                report.setPeriod("Weekly");
                report.setStartDate(weekStart);
                report.setEndDate(weekEnd);
                report.setTotalReservations(totalReservations);
                report.setTotalRevenue(totalRevenue);
                report.setReservationsByVehicleType(reservationsByVehicleTypeInt);
                report.setRevenueByVehicleType(revenueByVehicleType);

                reportDTOList.add(report);
            }
        }

        // Sort the reports by start date in ascending order
        reportDTOList.sort(Comparator.comparing(ReportDTO::getStartDate));

        // Create AggregatedReportDTO
        AggregatedReportDTO aggregatedReport = new AggregatedReportDTO("Weekly", reportDTOList);
        return aggregatedReport;
    }
}
