package com.example.exchanger.dto;

import com.example.exchanger.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private int id;
    @NonNull
    private String cc;
    @NonNull
    private String action;
    @NonNull
    private BigDecimal sumCurrency;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String status;
    @NonNull
    private int confirmationCode;
    @NonNull
    private BigDecimal sumPayment;
    @NonNull
    private LocalDate date;

    public Request toEntity() {
        return new Request(id, cc, action, sumCurrency, phoneNumber, status, confirmationCode, sumPayment, date);
    }
}
