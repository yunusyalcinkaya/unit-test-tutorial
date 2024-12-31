package com.example.unittesttutorial.service;

import com.example.unittesttutorial.dto.BuyProductResponse;

public interface CatalogService {

    BuyProductResponse buyProduct(String productCode);
    void deleteFromStock(String productCode);
}
