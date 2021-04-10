package com.example.exchanger.repository;

import com.example.exchanger.data.Request;
import com.example.exchanger.dto.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Request findByConfirmationCode(int code);

    Request findByPhoneNumber(String phoneNumber);

    Request findByPhoneNumberAndAction(String phone, String phoneNumber);

    @Query("select new com.example.exchanger.dto.Report(count (r.id) as countOperation, sum (r.sumPayment) as sum, r.action as action, r.cc as cc, r.date as date)" +
            "from Request r where r.date =:date group by r.cc, r.action, r.status having r.status like 'Выполнена'")
   List<Report> getReportByDay(@Param("date") LocalDate date);


    @Query("select new com.example.exchanger.dto.Report(count (r.id) as countOperation, sum (r.sumPayment) as sum, r.cc as cc, r.action as action, r.date as date)" +
            "from Request r where r.date >= :startDay and r.date <= :endDay group by r.cc, r.date, r.action, r.status having r.cc like :cc and r.status like 'Выполнена'")
    List<Report> getReportByDayAdnCC(@Param("startDay") LocalDate startDate, @Param("endDay") LocalDate endDate, @Param("cc") String cc);
}
