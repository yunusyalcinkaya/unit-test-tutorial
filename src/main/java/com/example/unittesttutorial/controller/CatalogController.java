package com.example.unittesttutorial.controller;

import com.example.unittesttutorial.dto.AddProductRequest;
import com.example.unittesttutorial.dto.BuyProductResponse;
import com.example.unittesttutorial.service.CatalogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog/v1")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<BuyProductResponse> buyProduct(@PathVariable String productCode) {
        return new ResponseEntity<>(catalogService.buyProduct(productCode), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addProduct(@Valid @RequestBody AddProductRequest request) {
        // ...
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
