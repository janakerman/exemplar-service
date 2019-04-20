package com.janakerman.exemplarservice.acceptance;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janakerman.exemplarservice.controller.PaymentController;
import com.janakerman.exemplarservice.dto.CreatePayment;
import com.janakerman.exemplarservice.dto.Payment;
import com.janakerman.exemplarservice.repository.PaymentRepository;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentTests extends BaseAcceptanceTests {

    @Autowired ObjectMapper objectMapper;

    @Autowired WebApplicationContext webApplicationContext;

    @Autowired PaymentController controller;

    @Autowired PaymentRepository repository;

    @Before
    public void setUpMockMvc() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void getPayment() {
        CreatePayment createPayment1 = CreatePayment.builder()
            .organisationId("org1")
            .build();

        Payment createdPayment = createPayment(createPayment1);

        Payment payment = given()
            .when()
                .get(String.format("/payments/%s", createdPayment.getId()))
            .then()
                .statusCode(200)
            .extract()
            .as(Payment.class);

        assertThat(payment.getId(), notNullValue());
        assertThat(payment.getOrganisationId(), equalTo(payment.getOrganisationId()));
    }

    @Test
    public void getPayments() {
        CreatePayment createPayment1 = CreatePayment.builder()
            .organisationId("org1")
            .build();

        CreatePayment createPayment2 = CreatePayment.builder()
            .organisationId("org2")
            .build();

        Payment createdPayment1 = createPayment(createPayment1);
        Payment createdPayment2 = createPayment(createPayment2);

        List<Payment> payments = given()
            .when()
                .get("/payments")
            .then()
                .statusCode(200)
            .extract()
                .body()
                .jsonPath().getList(".", Payment.class);

        assertThat(payments, hasSize(2));

        Payment payment1 = paymentMatchingId(payments, createdPayment1.getId());
        assertThat(payment1.getOrganisationId(), equalTo("org1"));

        Payment payment2 = paymentMatchingId(payments, createdPayment2.getId());
        assertThat(payment2.getOrganisationId(), equalTo("org2"));
    }

    @Test
    public void createPayment() {
        CreatePayment createPayment = CreatePayment.builder()
            .organisationId("org1")
            .build();

        Payment payment = createPayment(createPayment);

        assertThat(payment.getId(), notNullValue());
        assertThat(payment.getOrganisationId(), equalTo(createPayment.getOrganisationId()));

        com.janakerman.exemplarservice.domain.Payment paymentDomain = repository.findAll().get(0);
        assertThat(paymentDomain.getId(), notNullValue());
        assertThat(paymentDomain.getOrganisationId(), equalTo(createPayment.getOrganisationId()));
    }

    @Test
    public void deletePayment() {
        CreatePayment createPayment = CreatePayment.builder()
            .organisationId("org1")
            .build();

        Payment payment = createPayment(createPayment);

        given()
            .when()
                .delete(String.format("/payments/%s", payment.getId()))
            .then()
                .statusCode(200);

        assertThat(repository.findById(payment.getId()), nullValue());
    }

    private Payment createPayment(CreatePayment createPayment) {
        return given()
                .contentType(JSON)
                .body(createPayment)
            .when()
                .post("/payments")
            .then()
                .statusCode(200)
            .extract()
            .as(Payment.class);
    }

    private Payment paymentMatchingId(List<Payment> payments, String id) {
        Optional<Payment> matchedPayment = payments.stream().filter(payment -> payment.getId().equals(id)).findFirst();
        return matchedPayment.orElse(null);
    }

}
