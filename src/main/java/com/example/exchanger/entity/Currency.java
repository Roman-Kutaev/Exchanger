package com.example.exchanger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Currency {
    @Id
    @NonNull
    private String cc;
    @NonNull
    private BigDecimal purchase;
    @NonNull
    private BigDecimal sale;
    @NonNull
    private String exchangeDate;
}
