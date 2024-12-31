package com.example.unittesttutorial.service.impl;

import com.example.unittesttutorial.dto.BuyProductResponse;
import com.example.unittesttutorial.dto.PayRequest;
import com.example.unittesttutorial.external.PaymentService;
import com.example.unittesttutorial.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private static final List<String> EXISTS_PRODUCT_LIST = List.of("Computer", "Phone");

    private final PaymentService paymentService;

    @Override
    public BuyProductResponse buyProduct(String productCode) {
        if (EXISTS_PRODUCT_LIST.contains(productCode)) {
            paymentService.pay(new PayRequest());

            return new BuyProductResponse(UUID.randomUUID().toString(), productCode, 100);
        }
        return null;
    }

    @Override
    public void deleteFromStock(String productCode) {
        if (!EXISTS_PRODUCT_LIST.contains(productCode))
            throw new IllegalArgumentException("Product does not exist.");
    }
}
