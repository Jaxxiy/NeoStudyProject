package ru.tseytwa.neoproject.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

public class VacationsDTO {
    double avgSalary;
    int daysCount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate vacationStartDate;

    public double getAvgSalary() {
        return avgSalary;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public Optional<LocalDate> getVacationStartDate() {
        return Optional.ofNullable(vacationStartDate);
    }
}
