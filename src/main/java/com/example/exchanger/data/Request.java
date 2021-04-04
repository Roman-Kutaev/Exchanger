package com.example.exchanger.data;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Request {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cc;
    private String action;
    private BigDecimal sumCurrency;
    @NonNull
    private String phoneNumber;
    private String status;
    @NonNull
    private int confirmationCode;
    @NonNull
    private BigDecimal sumPayment;


}
