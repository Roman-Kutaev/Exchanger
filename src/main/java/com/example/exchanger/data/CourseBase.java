package com.example.exchanger.data;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class CourseBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @NonNull
    private Integer r030;
    @NonNull
    private String txt;
    @NonNull
    private BigDecimal rate;
    @NonNull
    private String cc;
    @NonNull
    private String exchangedate;

}
