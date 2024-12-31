package com.example.unittesttutorial.external.impl;

import com.example.unittesttutorial.dto.PayRequest;
import com.example.unittesttutorial.external.PaymentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;

    public PaymentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void pay(PayRequest payRequest) {
        Boolean response = restTemplate.exchange("payment-url", HttpMethod.POST, new HttpEntity<>(payRequest), Boolean.class).getBody();

        if (!Boolean.TRUE.equals(response)) {
            throw new RuntimeException("Payment failed.");
        }
    }
}
