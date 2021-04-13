package com.example.exchanger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping
    private String index() {
        return "index";
    }

    @GetMapping("/report")
    public String create() {
        return "report";
    }
}
