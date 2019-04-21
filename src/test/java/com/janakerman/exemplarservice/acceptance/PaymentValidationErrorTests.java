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
public class PaymentValidationErrorTests extends BaseAcceptanceTests {

    @Test
    public void testInvalidPaymentReturns400() {
        CreatePayment createPayment = CreatePayment.builder()
            .build();

        given()
            .contentType(JSON)
            .body(createPayment)
        .when()
            .post("/payments")
        .then()
            .statusCode(400);
    }

}
