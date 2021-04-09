package com.example.exchanger.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private long countOperation;
    private BigDecimal sum;
    private String action;
    private String cc;
    private LocalDate date;

}
