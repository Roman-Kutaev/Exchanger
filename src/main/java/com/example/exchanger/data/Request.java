package com.example.exchanger.data;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @NonNull
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

    private LocalDate date;

}
