package com.example.exchanger.api.controller;

import com.example.exchanger.data.Request;
import com.example.exchanger.dto.Report;
import com.example.exchanger.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exchanger/request")
@CrossOrigin(origins = "*", methods = RequestMethod.GET)
public class RequestController {

    private static final Logger LOGGER = LogManager.getLogger(RequestController.class);

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Request request) {
       return requestService.saveRequest(request);
    }

    @GetMapping()
    public List<Request> allRequest() {
        return requestService.findAllRequest();
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<Request> findRequestByCode(@PathVariable int code) {
        LOGGER.debug("code = " + code);
//        System.out.println("code = " + code);
        return requestService.findRequestByCode(code);
    }

//    @GetMapping(path = "/{phoneNumber/action}")
//    public ResponseEntity<Request> findRequestByCode(@PathVariable String phoneNumber,String action) {
//        System.out.println("phoneNumber = " + phoneNumber);
//        System.out.println("action = " + action);
//        return requestService.findByPhoneNumberAndAction(phoneNumber, action);
//    }

    @DeleteMapping(path = "/{phone}")
    public ResponseEntity<Request> deleteRequest(@PathVariable String phone){
        return requestService.deleteRequest(phone);
    }

    @GetMapping(path = "report")
    public List<Report> createReport(){
        return requestService.createReport();
    }

    @GetMapping(path = "report/{startDay}/{endDay}")
    public List<Report> createCustomReport(@PathVariable String startDay, @PathVariable String endDay){
        return requestService.createCustomReport(startDay, endDay);
    }
}
