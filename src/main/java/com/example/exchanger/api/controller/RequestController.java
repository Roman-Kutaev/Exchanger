package com.example.exchanger.api.controller;

import com.example.exchanger.entity.Request;
import com.example.exchanger.dto.Report;
import com.example.exchanger.dto.RequestDto;
import com.example.exchanger.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> create(@RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(requestService.saveRequest(requestDto));

    }

    @GetMapping()
    public List<Request> allRequest() {
        return requestService.findAllRequest();
    }

    @GetMapping(path = "/{id}/{code}")
    public ResponseEntity<Request> changStatusRequest(@PathVariable int id, @PathVariable int code) {
        Request request = requestService.changStatus(id, code);
        if (request.getStatus().equals(RequestService.STATUS_COMPLETED)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<List<Request>> deleteRequest() {
        return ResponseEntity.status(HttpStatus.OK).body(requestService.deleteAllBadRequest());
    }

    @GetMapping(path = "/report/{status}")
    public List<Request> allRequest(@PathVariable String status) {
        return requestService.findAllRequest(status);
    }


    @PreDestroy
    @GetMapping(path = "/report")
    public List<Report> createReport() {
        System.out.println("Report = " + requestService.createReport());
        return requestService.createReport();
    }

    @GetMapping(path = "report/{startDay}/{endDay}/{cc}")
    public List<Report> createCustomReport(@PathVariable String startDay, @PathVariable String endDay, @PathVariable String cc) {
        return requestService.createCustomReport(startDay, endDay, cc);
    }

}
