package com.janakerman.exemplarservice.acceptance;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janakerman.exemplarservice.dto.CreatePayment;
import com.janakerman.exemplarservice.service.PaymentService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentInternalErrorTests extends BaseAcceptanceTests {

    @MockBean PaymentService paymentService;

    @Test
    public void testInternalExceptionReturns500() {
        CreatePayment createPayment = CreatePayment.builder()
            .organisationId("org1")
            .amount("20.00")
            .build();

        when(paymentService.createPayment(anyObject())).thenThrow(new RuntimeException("test exception"));

        given()
            .contentType(JSON)
            .body(createPayment)
        .when()
            .post("/payments")
        .then()
            .statusCode(500);
    }

}
