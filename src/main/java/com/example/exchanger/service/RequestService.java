package com.example.exchanger.service;

import com.example.exchanger.data.Currency;
import com.example.exchanger.data.Request;
import com.example.exchanger.repository.CurrencyRepository;
import com.example.exchanger.repository.RequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Random;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final CurrencyRepository currencyRepository;
    //    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
    public static final String ACCOUNT_SID = "AC8f4c490f382770a6ef660050c391d95b";
    public static final String AUTH_TOKEN = "9a8c052ef0edb06a4dd9ef7cd8345f6f";
    public static final String TWILIO_NUMBER = "+13343846722";

    public RequestService(RequestRepository requestRepository, CurrencyRepository currencyRepository) {
        this.requestRepository = requestRepository;
        this.currencyRepository = currencyRepository;
//        restTemplate = new RestTemplate();
//        objectMapper = new ObjectMapper();
    }

    public List<Request> findAllRequest() {
        return requestRepository.findAll();
    }

    public ResponseEntity<?> save(Request request) {
        try {
            Random random = new Random();
            int code = random.nextInt(100) + 100;

            request.setStatusNew(true);
            request.setConfirmationCode(code);

            Currency currency = currencyRepository.findCourseByCc(request.getCc());
            BigDecimal sumPayment;
            if (request.getAction().equals("Продажа")) {
                sumPayment = currency.getPurchase().multiply(request.getSumCurrency());
                request.setSumPayment(sumPayment);
            } else if (request.getAction().equals("Покупка")) {
                sumPayment = currency.getSale().multiply(request.getSumCurrency());
                request.setSumPayment(sumPayment);
            } else request.setSumPayment(null);

            requestRepository.save(request);
            System.out.println("request = " + request);

            System.out.println("Вы подали заявку: " + request.getAction() + " " + request.getSumCurrency() + " " + request.getCc()
                    + "\nСумма к оплате " + request.getSumPayment() + " грн.\nВаш код подтверждения " + request.getConfirmationCode());
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Message message = Message.creator(
//                    new PhoneNumber(request.getPhoneNumber()),
//                    new PhoneNumber(TWILIO_NUMBER),
//                    "Вы подали заявку на " + request.getAction() + " " + request.getSumCurrency() + " " + request.getCc() +
//                            "\nСумма к оплате " + request.getSumCurrency() + " грн.\nВаш код подтверждения " + request.getConfirmationCode()).create();

            return ResponseEntity.created(URI.create("/api/v1/exchanger/request" + request.getId())).body(new Request(request.getPhoneNumber(), request.getConfirmationCode(), request.getSumPayment()));
        } catch (Throwable ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getLocalizedMessage());
        }
    }

    public ResponseEntity<?> findRequest(Request request) {
        request.setStatusNew(false);
        System.out.println("requestRepository.findRequestByCode(request.getConfirmationCode() = " + requestRepository.findByConfirmationCode(request.getConfirmationCode()));
        return ResponseEntity.ok(requestRepository.findByConfirmationCode(request.getConfirmationCode()));
    }

    public ResponseEntity<Request> findRequestByCode(int code) {
        Request request = requestRepository.findByConfirmationCode(code);
        if (request != null) {
            request.setStatusNew(false);
            requestRepository.save(request);
            System.out.println("Service request = " + request);
            return ResponseEntity.ok(request);
        } else {

//          return ResponseEntity.badRequest().body(null);
            System.out.println("ResponseEntity.notFound().build() " + ResponseEntity.notFound().build());
            return ResponseEntity.notFound().build();
        }
    }

}
