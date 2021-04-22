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

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cc;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "currency_id", referencedColumnName = "id")
//    private Currency currency;

    private String action;
    @Column(name = "sum_currency")
    @Range(min = 1)
    private BigDecimal sumCurrency;
    @NonNull
    @Column(name = "phone_number")

    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$")
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
