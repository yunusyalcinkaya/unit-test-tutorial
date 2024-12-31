package com.example.unittesttutorial.external.impl;

import com.example.unittesttutorial.dto.PayRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void pay_when_paymentSuccess_then_doesNotThrowException() {
        // given
        PayRequest request = new PayRequest();

        when(restTemplate.exchange("payment-url", HttpMethod.POST, new HttpEntity<>(request), Boolean.class))
                .thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

        // when - then
        assertDoesNotThrow(() -> paymentService.pay(request));
    }

    @Test
    void pay_when_paymentFailed_then_throwException() {
        // given
        PayRequest request = new PayRequest();

        when(restTemplate.exchange("payment-url", HttpMethod.POST, new HttpEntity<>(request), Boolean.class))
                .thenReturn(new ResponseEntity<>(false, HttpStatus.OK));

        // when
        RuntimeException actual = assertThrows(RuntimeException.class, () -> paymentService.pay(request));

        // then
        assertEquals("Payment failed.", actual.getMessage());
    }
}