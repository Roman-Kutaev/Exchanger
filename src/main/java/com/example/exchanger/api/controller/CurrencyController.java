package com.example.exchanger.api.controller;

import com.example.exchanger.data.Currency;
import com.example.exchanger.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exchanger")
@CrossOrigin(origins = "*", methods = RequestMethod.GET)
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping()
    public List<Currency> allCourse() {
        return currencyService.getAllCourse();
    }

    @GetMapping(path = "/{cc}")
    public ResponseEntity<Currency> currencyByCc(@PathVariable String cc) {
        System.out.println("currency = " + currencyService.findCourseBiCC(cc));
        return currencyService.findCourseBiCC(cc);
    }

//    @GetMapping(path = "/{id}")
//    public ResponseEntity<Course> courseById(@PathVariable int id) {
//       return courseService.findCourseById(id);
//    }
}
