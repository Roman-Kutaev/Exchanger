package com.example.exchanger.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Report {
    @NonNull
    private long countOperation;
    @NonNull
    private BigDecimal sum;
    @NonNull
    private String action;
    @NonNull
    private String cc;
    @NonNull
    private LocalDate date;


}
