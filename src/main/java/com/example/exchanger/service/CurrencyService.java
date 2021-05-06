package com.example.exchanger.service;

import com.example.exchanger.data.CourseBase;
import com.example.exchanger.data.Currency;
import com.example.exchanger.repository.CurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${url}")
    private String url;

    public CurrencyService(CurrencyRepository repository) {
        this.restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        this.currencyRepository = repository;
    }

    @Transactional
    public List<Currency> getAllCourse() {
        String jsonCourseArray = restTemplate.getForEntity(url, String.class).getBody();
        List<CourseBase> courseList = null;
        try {
            courseList = objectMapper.readValue(jsonCourseArray, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        currencyRepository.deleteAll();
        assert courseList != null;
        for (CourseBase course : courseList) {
            BigDecimal currencySale = course.getRate().multiply(new BigDecimal("1.05")).setScale(2, RoundingMode.UP);
            BigDecimal currencyPurchase = course.getRate().multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.UP);
            Currency currency = new Currency(course.getCc(), currencyPurchase, currencySale, course.getExchangedate());
            currencyRepository.save(currency);
        }
        return currencyRepository.findAll();
    }

    @Transactional
    public Currency findCourseByCC(String cc) {
        return currencyRepository.findCourseByCc(cc.toUpperCase());
    }

}
