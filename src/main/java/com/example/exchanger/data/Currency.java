package com.example.exchanger.data;

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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @NonNull
    private String cc;
    @NonNull
    private BigDecimal purchase;
    @NonNull
    private BigDecimal sale;
    @NonNull
    private String exchangeDate;
}
