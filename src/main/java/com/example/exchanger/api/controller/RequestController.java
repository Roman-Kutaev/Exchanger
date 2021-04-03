package com.example.exchanger.api.controller;

import com.example.exchanger.data.Request;
import com.example.exchanger.service.RequestService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exchanger/request")
@CrossOrigin(origins = "*", methods = RequestMethod.GET)
public class RequestController {
    private final RequestService requestService;



    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Request request) {

       return requestService.save(request);
    }

    @GetMapping()
    public List<Request> allRequest() {
        return requestService.findAllRequest();
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<Request> requestByCode(@PathVariable int code) {
        System.out.println("code = " + code);
        return requestService.findRequestByCode(code);

    }
}
