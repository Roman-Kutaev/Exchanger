package com.example.exchanger.service;

import com.example.exchanger.data.Request;
import com.example.exchanger.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.exchanger.service.RequestService.STATUS_COMPLETED;
import static com.example.exchanger.service.RequestService.STATUS_NEW;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @InjectMocks
    private RequestService service;

    @Mock
    private RequestRepository requestRepository;

    @Test
    void findAllRequest() {
        service.findAllRequest();
        verify(requestRepository).findAll();
    }

    @Test
    void saveRequest() {
    }

    @Test
    void changStatusSuccess() {
        int id = 0;
        int code = 123;
        Request request = new Request();
        request.setConfirmationCode(123);

        when(requestRepository.findById(eq(id))).thenReturn(Optional.of(request));
        ResponseEntity<Request> result = service.changStatus(id,code);

        verify(requestRepository, times(1)).saveAndFlush(any(Request.class));
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void changStatusSuccessWrong() {
        int id = 0;
        int code = 123;
        Request request = new Request();
        request.setConfirmationCode(321);

        when(requestRepository.findById(eq(id))).thenReturn(Optional.of(request));
        ResponseEntity<Request> result = service.changStatus(id,code);

        verify(requestRepository, times(1)).saveAndFlush(any(Request.class));
        assertEquals(400, result.getStatusCodeValue());
    }

    @Test
    void changStatusNotFound(){
        int id = 0;
        int code = 123;
        ResponseEntity<Request> result = service.changStatus(id, code);
        verify(requestRepository, never()).saveAndFlush(any(Request.class));
        assertEquals(404, result.getStatusCodeValue());
    }

//    @Test
//    void deleteRequestSuccess() {
//        String phone = "PHONE";
//        Request request = new Request();
//        request.setStatus(STATUS_NEW);
//
//        when(requestRepository.findByPhoneNumber(eq(phone))).thenReturn(request);
//        ResponseEntity<Request> result = service.deleteRequest(phone);
//
//        verify(requestRepository, times(1)).delete(any(Request.class));
//        assertEquals(200, result.getStatusCodeValue());
//    }

//    @Test
//    void deleteRequestWrongStatus() {
//
//        String phone = "PHONE";
//        Request request = new Request();
//        request.setStatus(STATUS_COMPLETED);
//
//        when(requestRepository.findByPhoneNumber(eq(phone))).thenReturn(request);
//        ResponseEntity<Request> result = service.deleteRequest(phone);
//
//        verify(requestRepository, never()).delete(any(Request.class));
//        assertEquals(204, result.getStatusCodeValue());
//    }

//    @Test
//    void deleteRequestNotFound() {
//        String phone = "PHONE";
//        ResponseEntity<Request> result = service.deleteRequest(phone);
//        assertEquals(404, result.getStatusCodeValue());
//    }

    @Test
    void createReport() {
        service.createReport();
        verify(requestRepository).getReportByDay(LocalDateTime.now().toLocalDate());
    }

    @Test
    void createCustomReport() {
        String startDate = "2021-04-02";
        String endDate = "2021-04-14";
        String cc = "usd";
        service.createCustomReport(startDate,endDate,cc);
        LocalDate startDay = LocalDate.parse(startDate);
        LocalDate endDay = LocalDate.parse(endDate);
        String ccFormat = cc.toUpperCase();
        verify(requestRepository).getReportByDayAdnCC(startDay,endDay,ccFormat);
    }
}