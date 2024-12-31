package com.example.unittesttutorial.service.impl;

import com.example.unittesttutorial.dto.BuyProductResponse;
import com.example.unittesttutorial.external.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogServiceImplTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    CatalogServiceImpl catalogService;

    @Test
    void buyProduct_when_paymentIsSuccess_then_returnProductInfo() {
        // given
        UUID uuid = UUID.randomUUID();
        BuyProductResponse expected = new BuyProductResponse(uuid.toString(), "Computer", 100);


        doNothing().when(paymentService).pay(any());

        try(MockedStatic<UUID> uuidMockedStatic = mockStatic(UUID.class)) {
            uuidMockedStatic.when(UUID::randomUUID).thenReturn(uuid);

            // when
            BuyProductResponse actual = catalogService.buyProduct("Computer");

            // then
            assertAll(
                    () -> assertEquals(actual.getId(), expected.getId()),
                    () -> assertEquals(actual.getCode(), expected.getCode()),
                    () -> assertEquals(actual.getAmount(), expected.getAmount()),
                    () -> verify(paymentService).pay(any()));
        }
    }

    @Test
    void buyProduct_when_paymentIsFailed_then_throwException() {
        // given
        doThrow(new RuntimeException("Payment failed.")).when(paymentService).pay(any());
        // when
        RuntimeException actual = assertThrows(RuntimeException.class, () -> catalogService.buyProduct("Computer"));

        // then
        assertAll(
                () -> assertEquals("Payment failed.", actual.getMessage()),
                () -> verify(paymentService).pay(any())
        );
    }

    @Test
    void buyProduct_when_givenInvalidProductCode_then_returnNull() {
        // given - when
        BuyProductResponse actual = catalogService.buyProduct("invalid-code");

        // then
        assertAll(
                () -> assertNull(actual),
                () -> verify(paymentService, never()).pay(any())
        );
    }

    @Test
    void deleteFromStock_when_productCodeIsValid_then_doosNotThrowException() {
        // given - when - then
        assertDoesNotThrow(() -> catalogService.deleteFromStock("Computer"));
    }

    @Test
    void deleteFromStock_when_productCodeIsInvalid_then_throwException() {
        // given - when
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class, () -> catalogService.deleteFromStock("invalid-code"));

        // then
        assertEquals("Product does not exist.", actual.getMessage());
    }
}