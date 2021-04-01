package com.example.exchanger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping
    private String index(Model model) {
        model.addAttribute("time", LocalDateTime.now().toLocalTime());
//        List<OurBankRate> purchaseList = courseService.getAllCourse();
//        model.addAttribute("courses", purchaseList);

        return "index";
    }
}
