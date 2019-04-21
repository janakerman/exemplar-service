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
import com.janakerman.exemplarservice.dto.UpdatePayment;
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
        Payment createdPayment = createPayment(createPaymentBuilder().build());

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
        Payment createdPayment1 = createPayment(createPaymentBuilder().build());
        Payment createdPayment2 = createPayment(
            createPaymentBuilder()
            .organisationId("org2")
            .build()
        );

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
        assertThat(payment1.getOrganisationId(), equalTo(createdPayment1.getOrganisationId()));

        Payment payment2 = paymentMatchingId(payments, createdPayment2.getId());
        assertThat(payment2.getOrganisationId(), equalTo(createdPayment2.getOrganisationId()));
    }

    @Test
    public void createPayment() {
        CreatePayment createCommand = createPaymentBuilder().build();
        Payment payment = createPayment(createCommand);

        assertThat(payment.getId(), notNullValue());
        assertThat(payment.getOrganisationId(), equalTo(createCommand.getOrganisationId()));

        com.janakerman.exemplarservice.domain.Payment paymentDomain = repository.findAll().get(0);
        assertThat(paymentDomain.getId(), notNullValue());
        assertThat(paymentDomain.getOrganisationId(), equalTo(createCommand.getOrganisationId()));
    }

    @Test
    public void updatePayment() {
        Payment createdPayment = createPayment(
            createPaymentBuilder()
                .build()
        );

        UpdatePayment updateCommand = updatePaymentBuilder()
            .id(createdPayment.getId())
            .build();

        Payment updatedPayment = given()
            .contentType(JSON)
            .body(updateCommand)
        .when()
            .put("/payments")
        .then()
            .statusCode(200)
        .extract()
            .as(Payment.class);

        assertThat(updatedPayment.getId(), equalTo(createdPayment.getId()));
        assertThat(updatedPayment.getOrganisationId(), equalTo(updateCommand.getOrganisationId()));

        com.janakerman.exemplarservice.domain.Payment paymentDomain = repository.findAll().get(0);
        assertThat(paymentDomain.getId(), equalTo(updateCommand.getId()));
        assertThat(paymentDomain.getOrganisationId(), equalTo(updateCommand.getOrganisationId()));
    }

    @Test
    public void partialUpdatePayment() {
        CreatePayment createCommand = createPaymentBuilder()
            .organisationId("org1")
            .build();
        Payment createdPayment = createPayment(createCommand);

        UpdatePayment updateCommand = UpdatePayment.builder()
            .id(createdPayment.getId())
            .organisationId("org2")
            .build();

        Payment updatedPayment = given()
            .contentType(JSON)
            .body(updateCommand)
        .when()
            .patch("/payments")
        .then()
            .statusCode(200)
        .extract()
            .as(Payment.class);

        com.janakerman.exemplarservice.domain.Payment paymentDomain = repository.findAll().get(0);

        // Organisation field is updated.
        assertThat(updatedPayment.getOrganisationId(), equalTo(updateCommand.getOrganisationId()));
        assertThat(paymentDomain.getOrganisationId(), equalTo(updateCommand.getOrganisationId()));

        // Original fields are unchanged from create.
        assertThat(updatedPayment.getId(), equalTo(createdPayment.getId()));

        assertThat(paymentDomain.getId(), equalTo(createdPayment.getId()));
    }

    @Test
    public void deletePayment() {
        Payment payment = createPayment(createPaymentBuilder().build());

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

    private CreatePayment.CreatePaymentBuilder createPaymentBuilder() {
        return CreatePayment.builder()
            .organisationId("org1");
    }

    private UpdatePayment.UpdatePaymentBuilder updatePaymentBuilder() {
        return UpdatePayment.builder()
            .id("id")
            .organisationId("org1");
    }

}
