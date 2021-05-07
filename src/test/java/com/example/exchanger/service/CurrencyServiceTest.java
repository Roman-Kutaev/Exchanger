package com.example.exchanger.service;


import com.example.exchanger.repository.CurrencyRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService service;

    @Mock
    private CurrencyRepository currencyRepository;

//    @Test
//    void findCourseByCCSuccess(){
//        String cc = "USD";
//        Currency currency = new Currency();
//        when(currencyRepository.findCourseByCc(eq(cc))).thenReturn(currency);
//        ResponseEntity<Currency> result = service.findCourseByCC(cc);
//
//        assertEquals(200, result.getStatusCodeValue());
//    }
//
//    @Test
//    void findCourseByCCNotFound(){
//        String cc = "";
//        ResponseEntity<Currency> result = service.findCourseByCC(cc);
//        assertEquals(404,result.getStatusCodeValue());
//    }
}
