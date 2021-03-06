package com.janakerman.exemplarservice.controller;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import com.janakerman.exemplarservice.domain.Amount;
import com.janakerman.exemplarservice.exception.PaymentValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janakerman.exemplarservice.dto.CreatePayment;
import com.janakerman.exemplarservice.dto.Payment;
import com.janakerman.exemplarservice.exception.PaymentNotFoundException;
import com.janakerman.exemplarservice.service.PaymentService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PaymentControllerTests {

    @Autowired PaymentController controller;

    @MockBean PaymentService paymentService;

    private List<com.janakerman.exemplarservice.domain.Payment> testPayments = new ArrayList<>();
    private com.janakerman.exemplarservice.domain.Payment testPayment1;
    private com.janakerman.exemplarservice.domain.Payment testPayment2;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(controller);

        testPayment1 = com.janakerman.exemplarservice.domain.Payment.builder()
            .id("id1")
            .organisationId("org1")
            .amount(Amount.of("20.00", "GBP"))
            .build();

        testPayment2 = com.janakerman.exemplarservice.domain.Payment.builder()
            .id("id2")
            .organisationId("org2")
            .amount(Amount.of("30.00", "JOD"))

            .build();

        testPayments.add(testPayment1);
        testPayments.add(testPayment2);
    }

    @Test
    public void getPayment() {
        when(paymentService.getPayment(anyString()))
            .thenReturn(testPayment1);

        Payment payment = given()
            .when()
                .get("/payments/AN_ID")
            .then()
                .statusCode(200)
            .extract()
                .as(Payment.class);

        assertThat(payment.getId(), equalTo(testPayment1.getId()));
        assertThat(payment.getOrganisationId(), equalTo(testPayment1.getOrganisationId()));
        assertThat(payment.getAmount().getValue(), equalTo(testPayment1.getAmount().getAmount().toString()));
        assertThat(payment.getAmount().getCurrencyCode(), equalTo(testPayment1.getAmount().getCurrency().toString()));
    }

    @Test
    public void getPaymentNonExistantReturns404() {
        when(paymentService.getPayment(anyString()))
            .thenThrow(new PaymentNotFoundException());

        given()
            .when()
                .get("/payments/AN_ID")
            .then()
                .statusCode(404);
    }

    @Test
    public void getPayments() {
        when(paymentService.getPayments()).thenReturn(testPayments);

        List<Payment> payments = given()
            .when()
            .get("/payments")
            .then()
                .statusCode(200)
            .extract()
                .body()
                .jsonPath().getList(".", Payment.class);

        assertThat(payments, hasSize(2));

        Payment payment1 = paymentMatchingId(payments, testPayment1.getId());
        assertThat(payment1.getId(), equalTo(testPayment1.getId()));
        assertThat(payment1.getOrganisationId(), equalTo(testPayment1.getOrganisationId()));
        assertThat(payment1.getAmount().getValue(), equalTo(testPayment1.getAmount().getAmount().toString()));
        assertThat(payment1.getAmount().getCurrencyCode(), equalTo(testPayment1.getAmount().getCurrency().toString()));

        Payment payment2 = paymentMatchingId(payments, testPayment2.getId());
        assertThat(payment2.getId(), equalTo(testPayment2.getId()));
        assertThat(payment2.getOrganisationId(), equalTo(testPayment2.getOrganisationId()));
        assertThat(payment2.getAmount().getValue(), equalTo("30.000"));
        assertThat(payment2.getAmount().getCurrencyCode(), equalTo("JOD"));
    }

    @Test
    public void getPaymentsEmptyReturnsEmptyResults() {
        when(paymentService.getPayments()).thenReturn(new ArrayList<>());

        List<Payment> payments = given()
            .when()
                .get("/payments")
            .then()
                .statusCode(200)
            .extract()
                .body()
                .jsonPath().getList(".", Payment.class);

        assertThat(payments, hasSize(0));
    }

    @Test
    public void createPayment() {
        CreatePayment createPayment = CreatePayment.builder()
            .organisationId("org1")
            .amount(of("20.00", "GBP"))
            .build();

        when(paymentService.createPayment(notNull())).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = given()
            .contentType(JSON)
            .body(createPayment)
        .when()
            .post("/payments")
        .then()
            .statusCode(200)
        .extract()
            .as(Payment.class);

        assertThat(payment.getId(), notNullValue());
        assertThat(payment.getOrganisationId(), equalTo(createPayment.getOrganisationId()));

        ArgumentCaptor<com.janakerman.exemplarservice.domain.Payment> argumentCaptor = ArgumentCaptor.forClass(com.janakerman.exemplarservice.domain.Payment.class);
        verify(paymentService).createPayment(argumentCaptor.capture());
        com.janakerman.exemplarservice.domain.Payment savedPayment = argumentCaptor.getValue();

        assertThat(savedPayment.getId(), notNullValue());
        assertThat(savedPayment.getOrganisationId(), equalTo(createPayment.getOrganisationId()));
        assertThat(savedPayment.getAmount().getAmount(), equalTo(new BigDecimal(createPayment.getAmount().getValue())));
        assertThat(savedPayment.getAmount().getCurrency(), equalTo(Currency.getInstance(createPayment.getAmount().getCurrencyCode())));
    }

    @Test
    public void getPayments_InvalidPaymentReturns400() {
        when(paymentService.getPayments())
                .thenThrow(new PaymentValidationException());

        given()
                .when()
                .get("/payments")
            .then()
                .statusCode(400);
    }

    public Payment paymentMatchingId(List<Payment> payments, String id) {
        Optional<Payment> matchedPayment = payments.stream().filter(payment -> payment.getId().equals(id)).findFirst();
        return matchedPayment.orElse(null);
    }

    private com.janakerman.exemplarservice.dto.Amount of(String amount, String currencyCode) {
        return com.janakerman.exemplarservice.dto.Amount.builder()
                .value(amount)
                .currencyCode(currencyCode)
                .build();
    }

}
