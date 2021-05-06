package com.example.exchanger.api.controller;

import com.example.exchanger.data.Currency;
import com.example.exchanger.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<Currency>> allCourse() {
        List<Currency> currencyList = new ArrayList<>();
        if (currencyList != null){
            return ResponseEntity.status(HttpStatus.OK).body(currencyService.getAllCourse());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(path = "/{cc}")
    public ResponseEntity<Currency> currencyByCc(@PathVariable String cc) {
        Currency currency = currencyService.findCourseByCC(cc);
        if (currency != null){
            return ResponseEntity.status(HttpStatus.OK).body(currency);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

//    @GetMapping(path = "/{id}")
//    public ResponseEntity<Course> courseById(@PathVariable int id) {
//       return courseService.findCourseById(id);
//    }
}
