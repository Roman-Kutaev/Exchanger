package com.example.exchanger.data;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    private final String PHONE_FORMAT = "^\\+[1-9]{1}[0-9]{3,14}$";

    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cc;
    private String action;
    @Column(name = "sum_currency")
    @Range(min = 1)
    private BigDecimal sumCurrency;
    @NonNull
    @Column(name = "phone_number")
    @Pattern(regexp = PHONE_FORMAT)
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
