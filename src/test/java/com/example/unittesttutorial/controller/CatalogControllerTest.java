package com.example.unittesttutorial.controller;

import com.example.unittesttutorial.dto.AddProductRequest;
import com.example.unittesttutorial.dto.BuyProductResponse;
import com.example.unittesttutorial.service.CatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CatalogService catalogService;

    @Test
    void buyProduct_when_givenProductCode_then_return200AndProductInfo() throws Exception {
        // given
        String id = "sample-uuid";
        String code = "Computer";
        BuyProductResponse response = new BuyProductResponse(id, code, 10);

        when(catalogService.buyProduct(code)).thenReturn(response);

        // when - then
        mockMvc.perform(get("/catalog/v1/Computer"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.code").value(code),
                        jsonPath("$.amount").value(10));
    }

    @Test
    void addProduct_when_givenValidRequest_then_return200AndProductInfo() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest("Computer", 10);

        // when - then
        mockMvc.perform(post("/catalog/v1/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isOk(),
                        content().string("true"));
    }

    @Test
    void addProduct_when_givenInvalidAmount_then_return400() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest("Computer", 0);

        // when - then
        mockMvc.perform(post("/catalog/v1/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addProduct_when_givenInvalidCode_then_return400() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest("", 10);

        // when - then
        mockMvc.perform(post("/catalog/v1/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}