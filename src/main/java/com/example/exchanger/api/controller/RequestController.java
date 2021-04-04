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
       return requestService.saveRequest(request);
    }

    @GetMapping()
    public List<Request> allRequest() {
        return requestService.findAllRequest();
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<Request> findRequestByCode(@PathVariable int code) {
        System.out.println("code = " + code);
        return requestService.findRequestByCode(code);
    }
    @GetMapping(path = "/{phoneNumber/action}")
    public ResponseEntity<Request> findRequestByCode(@PathVariable String phoneNumber,String action) {
        System.out.println("phoneNumber = " + phoneNumber);
        System.out.println("action = " + action);
        return requestService.findByPhoneNumberAndAction(phoneNumber, action);
    }

    @DeleteMapping(path = "/{phone}")
    public ResponseEntity<Request> findRequestByPhone(@PathVariable String phone){
        return requestService.findRequestByPhone(phone);
    }
}
