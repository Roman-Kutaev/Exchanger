package com.example.exchanger.data;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cc;
    private String action;
    @Column(name = "sum_currency")
    private BigDecimal sumCurrency;
    @NonNull
    @Column(name = "phone_number")
    private String phoneNumber;
    private String status;
    @NonNull
    @Column(name = "confirmation_code")
    private int confirmationCode;
    @NonNull
    @Column(name = "sum_payment")
    private BigDecimal sumPayment;

}
