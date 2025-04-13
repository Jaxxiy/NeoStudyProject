package ru.tseytwa.neoproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import ru.tseytwa.neoproject.service.VacationsService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class NeoprojectApplicationTests {

	VacationsService vacationsService;

	@BeforeEach
	void setUp() throws IOException {
		vacationsService = new VacationsService(new ClassPathResource("static/holidays.csv"));
	}

	@Test
	void calculateVacationPayWithoutStartDate() {
		double averageSalary = 20000;
		int days = 10;
		double expectedVacationPay = 5720.55;
		Assertions.assertEquals(expectedVacationPay, vacationsService.calculateVacationPay(averageSalary,days, Optional.empty()));
	}

	@Test
	void calculateVacationPayWithStartDateAndWeekends() {
		double averageSalary = 30000;
		int days = 7;
		LocalDate startDate = LocalDate.of(2025, 3, 1);
		double expectedVacationPay = 4290.41;
		Assertions.assertEquals(expectedVacationPay, vacationsService.calculateVacationPay(averageSalary,days, Optional.of(startDate)));
	}

	@Test
	void calculateVacationPayWithStartDateAndHolidays() {
		double averageSalary = 30000;
		int days = 5;
		LocalDate startDate = LocalDate.of(2025, 1, 1);
		double expectedVacationPay = 0;
		Assertions.assertEquals(expectedVacationPay, vacationsService.calculateVacationPay(averageSalary,days, Optional.of(startDate)));
	}


}
