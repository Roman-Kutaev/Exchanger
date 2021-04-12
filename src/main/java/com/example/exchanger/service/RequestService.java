package com.example.exchanger.service;

import com.example.exchanger.data.Currency;
import com.example.exchanger.data.Request;
import com.example.exchanger.dto.Report;
import com.example.exchanger.repository.CurrencyRepository;
import com.example.exchanger.repository.RequestRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class RequestService implements ApplicationContextAware {
    private ApplicationContext context;
    private final RequestRepository requestRepository;
    private final CurrencyRepository currencyRepository;
//    public static final String ACCOUNT_SID = "AC8f4c490f382770a6ef660050c391d95b";
//    public static final String AUTH_TOKEN = "a61ced920a56505df18b276e193cddfc";
//    public static final String TWILIO_NUMBER = "+13343846722";
    public static final String STATUS_NEW = "Новая";
    public static final String STATUS_COMPLETED = "Выполнена";
    public static final String STATUS_CANCELED = "Отменена";
    public static final String ACTION_PURCHASE = "Продажа";
    public static final String ACTION_SALE = "Покупка";
    public static final Integer RANDOM_RANGE = 1000;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public RequestService(RequestRepository requestRepository, CurrencyRepository currencyRepository) {
        this.requestRepository = requestRepository;
        this.currencyRepository = currencyRepository;
    }

    public List<Request> findAllRequest() {
        return requestRepository.findAll();
    }

    @Transactional()
    public ResponseEntity<?> saveRequest(Request request) {
        try {
            Random random = new Random();
            int code = random.nextInt(RANDOM_RANGE) + RANDOM_RANGE;

            request.setConfirmationCode(code);

            request.setStatus(STATUS_NEW);

            Currency currency = currencyRepository.findCourseByCc(request.getCc());
            BigDecimal sumPayment;
            if (request.getAction().equals(ACTION_PURCHASE)) {
                sumPayment = currency.getPurchase().multiply(request.getSumCurrency());
                request.setSumPayment(sumPayment);
            } else if (request.getAction().equals(ACTION_SALE)) {
                sumPayment = currency.getSale().multiply(request.getSumCurrency());
                request.setSumPayment(sumPayment);
            } else {
                request.setSumPayment(null);
            }
            request.setDate(LocalDateTime.now().toLocalDate());

            requestRepository.save(request);
            System.out.println("Save request = " + request);

            System.out.println(request.getDate()+ "Вы подали заявку: " + request.getAction() + " " + request.getSumCurrency() + " " + request.getCc()
                    + "\nСумма к оплате " + request.getSumPayment() + " грн.\nВаш код подтверждения " + request.getConfirmationCode());
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Message message = Message.creator(
//                    new PhoneNumber(request.getPhoneNumber()),
//                    new PhoneNumber(TWILIO_NUMBER),
//                    "Вы подали заявку на " + request.getAction() + " " + request.getSumCurrency() + " " + request.getCc() +
//                            "\nСумма к оплате " + request.getSumCurrency() + " грн.\nВаш код подтверждения " + request.getConfirmationCode()).create();

            return ResponseEntity.status(HttpStatus.OK).body(new Request(request.getId(), request.getPhoneNumber(), request.getConfirmationCode(), request.getSumPayment()));
        } catch (Throwable ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
        }
    }

    public ResponseEntity<Request> changStatus(Request newRequest) {
        Request request  = requestRepository.findById(newRequest.getId()).orElse(null);
        assert request != null;
        if (newRequest.getConfirmationCode() == request.getConfirmationCode()){
            request.setStatus(STATUS_COMPLETED);
            requestRepository.saveAndFlush(request);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            request.setStatus(STATUS_CANCELED);
            requestRepository.saveAndFlush(request);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    public ResponseEntity<Request> deleteRequest(String phone){
        try {
            Request request = requestRepository.findByPhoneNumber(phone);
            if (request.getStatus().equals(STATUS_NEW)){
                requestRepository.delete(request);
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(204).build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    public List<Report> createReport(){
        LocalDate date = LocalDateTime.now().toLocalDate();
        return requestRepository.getReportByDay(date);
    }

    public List<Report> createCustomReport(String startDay, String endDay, String cc){
        LocalDate startLocalDate = LocalDate.parse(startDay);
        LocalDate endLocalDate = LocalDate.parse(endDay);
        return requestRepository.getReportByDayAdnCC(startLocalDate, endLocalDate, cc.toUpperCase());
    }

    public void shutDown(){
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) context;
        ctx.close();
    }

}
