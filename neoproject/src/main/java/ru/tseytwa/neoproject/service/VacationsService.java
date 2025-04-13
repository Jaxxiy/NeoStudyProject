package ru.tseytwa.neoproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class VacationsService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final Set<LocalDate> holidays;
    private final double NDS = 0.13;

    public VacationsService(@Value("classpath:static/holidays.csv") Resource holidaysResource) throws IOException {
        this.holidays = loadHolidays(holidaysResource);
    }

    public double calculateVacationPay(double averageSalary, Integer vacationDays, Optional<LocalDate> vacationStartDate) {
        double dailySalary = averageSalary*12/365;
        int workingDays = vacationDays;

        if (vacationStartDate.isPresent()) {
            workingDays = calculateAmountWorkingDays(vacationStartDate.get(), vacationDays);
        }

        return new BigDecimal(dailySalary*workingDays*(1-NDS)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private Set<LocalDate> loadHolidays(Resource holidaysResource) throws IOException {
        Set<LocalDate> holidays = new HashSet<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(holidaysResource.getInputStream()))){
            String line;
            while((line = bufferedReader.readLine()) != null) {
                try {
                    LocalDate holidayDate = LocalDate.parse(line);
                    holidays.add(holidayDate);
                }catch(Exception e) {
                    LOGGER.warn("Error parsing holiday date: " + line);
                }
            }
        }
        return holidays;
    }

    private int calculateAmountWorkingDays(LocalDate startDate, int daysCount) {
        int workingDays = 0;
        LocalDate currentDate = startDate;
        for (int i = 0; i<daysCount; i++) {
            if (!isHoliday(currentDate) && !isWeekend(currentDate)) { workingDays++;}
            currentDate = currentDate.plusDays(1);
        }
        return workingDays;
    }

    private boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }


}
