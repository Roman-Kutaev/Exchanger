package com.example.exchanger.service;

import com.example.exchanger.data.Currency;
import com.example.exchanger.data.Request;
import com.example.exchanger.dto.Report;
import com.example.exchanger.repository.CurrencyRepository;
import com.example.exchanger.repository.RequestRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final CurrencyRepository currencyRepository;
//    public static final String ACCOUNT_SID = "AC8f4c490f382770a6ef660050c391d95b";
//    public static final String AUTH_TOKEN = "4855d6c20ed79932464b2b79eb22d748";
//    public static final String TWILIO_NUMBER = "+13343846722";
    public static final String STATUS_NEW = "Новая";
    public static final String STATUS_COMPLETED = "Выполнена";
    public static final String STATUS_CANCELED = "Отменена";
    public static final String ACTION_PURCHASE = "Продажа";
    public static final String ACTION_SALE = "Покупка";
    public static final Integer RANDOM_RANGE = 1000;

    public RequestService(RequestRepository requestRepository, CurrencyRepository currencyRepository) {
        this.requestRepository = requestRepository;
        this.currencyRepository = currencyRepository;
    }

    public List<Request> findAllRequest() {
        return requestRepository.findAll();
    }

    @Transactional()
    public Request saveRequest(Request request) {
        try {
            Random random = new Random();
            int code = random.nextInt(RANDOM_RANGE);

            request.setConfirmationCode(code);

            request.setStatus(STATUS_NEW);

            Currency currency = currencyRepository.findCourseByCc(request.getCc());
            if (request.getAction().equals(ACTION_PURCHASE)) {
                request.setSumPayment(currency.getSale().multiply(request.getSumCurrency()));
            } else if (request.getAction().equals(ACTION_SALE)) {
                request.setSumPayment(currency.getPurchase().multiply(request.getSumCurrency()));
            } else {
                throw new Throwable();
            }
            request.setDate(LocalDateTime.now().toLocalDate());

            requestRepository.save(request);
            System.out.println("Save request = " + request);

            System.out.println("Вы подали заявку: " + request.getAction() + " " + request.getSumCurrency() + " " + request.getCc()
                    + "\nСумма к оплате " + request.getSumPayment() + " грн.\nВаш код подтверждения " + request.getConfirmationCode());
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Message message = Message.creator(
//                    new PhoneNumber(request.getPhoneNumber()),
//                    new PhoneNumber(TWILIO_NUMBER),
//                    "Вы подали заявку на " + request.getAction() + " " + request.getSumCurrency() + " " + request.getCc() +
//                            "\nСумма к оплате " + request.getSumPayment() + " грн.\nВаш код подтверждения " + request.getConfirmationCode()).create();

            return new Request(request.getId(), request.getPhoneNumber(), request.getConfirmationCode(), request.getSumPayment());
        } catch (Throwable ex) {
            return null;
        }
    }

    @Transactional
    public Request changStatus(int id, int code) {
        Request request = requestRepository.findById(id).orElse(null);
        assert request != null;
        if (code == request.getConfirmationCode()) {
            request.setStatus(STATUS_COMPLETED);
//                requestRepository.saveAndFlush(request);
//                System.out.println("Service request = " + request);
//                return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            request.setStatus(STATUS_CANCELED);
//                requestRepository.saveAndFlush(request);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        requestRepository.saveAndFlush(request);
        return request;
    }


    public List<Report> createReport() {
        LocalDate date = LocalDateTime.now().toLocalDate();
        return requestRepository.getReportByDay(date);
    }

    public List<Report> createCustomReport(String startDay, String endDay, String cc) {
        LocalDate startDate = LocalDate.parse(startDay);
        LocalDate endDate = LocalDate.parse(endDay);
        String ccFormat = cc.toUpperCase();
        return requestRepository.getReportByDayAdnCC(startDate, endDate, ccFormat);
    }

    public List<Request> findAllRequest(String status) {
        return requestRepository.findAllByStatus(status);
    }

    @Transactional
    public List<Request> deleteAllBadRequest() {
        try {
          return requestRepository.deleteByStatus(STATUS_CANCELED);

        } catch (Exception e) {
            return null;
        }

    }

}