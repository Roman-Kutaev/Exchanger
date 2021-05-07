package com.example.exchanger.service;

import com.example.exchanger.dto.CourseBase;
import com.example.exchanger.entity.Currency;
import com.example.exchanger.repository.CurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrencyService {
    private static final Double SALE_COEFFICIENT = 1.05;
    private static final Double PURCHASE_COEFFICIENT = 0.95;
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

        assert courseList != null;
        for (CourseBase course : courseList) {
            BigDecimal currencySale = course.getRate().multiply(new BigDecimal(SALE_COEFFICIENT)).setScale(2, RoundingMode.UP);
            BigDecimal currencyPurchase = course.getRate().multiply(new BigDecimal(PURCHASE_COEFFICIENT)).setScale(2, RoundingMode.UP);
            Currency currency = new Currency(course.getCc(), currencyPurchase, currencySale, course.getExchangedate());

            currencyRepository.save(currency);
        }
        return currencyRepository.findAll();
    }

    @Transactional
    public Currency findCourseById(String cc) {
        return currencyRepository.findById(cc.toUpperCase()).orElse(null);
    }

}
