package com.example.exchanger.api.controller;

import com.example.exchanger.data.Request;
import com.example.exchanger.dto.Report;
import com.example.exchanger.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PreDestroy;
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
        try {
            return requestService.saveRequest(request);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Введены не корректные данные!", ex);
        }
    }

    @GetMapping()
    public List<Request> allRequest() {
        return requestService.findAllRequest();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Request> changStatusRequest(@RequestBody Request request) {
        return requestService.changStatus(request);
    }

    @DeleteMapping(path = "delete/{phone}")
    public ResponseEntity<Request> deleteRequest(@PathVariable String phone) {
        return requestService.deleteRequest(phone);
    }

    @PreDestroy
    @GetMapping(path = "report")
    public List<Report> createReport() {
        System.out.println("Report = " + requestService.createReport());
        return requestService.createReport();
    }

    @GetMapping(path = "report/{startDay}/{endDay}/{cc}")
    public List<Report> createCustomReport(@PathVariable String startDay, @PathVariable String endDay, @PathVariable String cc) {
        return requestService.createCustomReport(startDay, endDay, cc);
    }

    @PostMapping("/shutDownContext")
    public void shutDownContext() {
        requestService.shutDown();
    }

}
