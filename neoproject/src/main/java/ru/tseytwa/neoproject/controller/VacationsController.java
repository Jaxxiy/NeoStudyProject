package ru.tseytwa.neoproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tseytwa.neoproject.dto.VacationsDTO;
import ru.tseytwa.neoproject.service.VacationsService;

@RestController
public class VacationsController {

    public VacationsService vacationsService;

    public VacationsController(VacationsService vacationsService) {
        this.vacationsService = vacationsService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<Double> calculate(@RequestBody VacationsDTO weekendsDTO) {
        Double weekendsPay = vacationsService.calculateVacationPay(weekendsDTO.getAvgSalary(),weekendsDTO.getDaysCount(),weekendsDTO.getVacationStartDate());
        return ResponseEntity.ok(weekendsPay);
    }
}
