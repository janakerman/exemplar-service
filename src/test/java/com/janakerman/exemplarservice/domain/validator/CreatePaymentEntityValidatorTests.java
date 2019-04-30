package com.janakerman.exemplarservice.domain.validator;

import java.util.UUID;

import com.janakerman.exemplarservice.domain.Amount;
import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class CreatePaymentEntityValidatorTests {

    private final CreatePaymentEntityValidator createPaymentEntityValidator = new CreatePaymentEntityValidator();

    @Test
    public void validateCreatePaymentAllFields() {
        Payment payment = validPayment()
                .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentIdRequired() {
        Payment payment = Payment.builder()
                .organisationId("organisationId")
                .amount(Amount.of("20.00", "GBP"))
                .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentOrganisationIdRequired() {
        Payment payment = Payment.builder()
                .id("id")
                .amount(Amount.of("20", "GBP"))
                .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentHasValidUUID() {
        Payment payment = validPayment()
                .id("not a uuid")
                .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentAmountRequired() {
        Payment payment = Payment.builder()
                .id("id")
                .organisationId("organisationId")
                .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test
    public void validateCreatePaymentGBPAmountValid_2DP() {
        Payment payment = validPayment()
            .amount(Amount.of("20.00", "GBP"))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test
    public void validateCreatePaymentGBPAmountValid_Integer() {
        Payment payment = validPayment()
            .amount(Amount.of("20", "GBP"))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentGBPAmountInvalid_Zero() {
        Payment payment = validPayment()
                .amount(Amount.of("0", "GBP"))
                .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentGBPAmountInvalid_Negative() {
        Payment payment = validPayment()
            .amount(Amount.of("-1", "GBP"))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentGBPAmountInvalid_GreaterThanDefaultDP() {
        Payment payment = validPayment()
            .amount(Amount.of("20.001", "GBP"))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentJODAmountInvalid_GreaterThanDefaultDP() {
        Payment payment = validPayment()
                .amount(Amount.of("20.0001", "JOD"))
                .build();
        createPaymentEntityValidator.validate(payment);
    }

    private Payment.PaymentBuilder validPayment() {
        return Payment.builder()
            .id(UUID.randomUUID().toString())
            .organisationId("organsationId")
            .amount(Amount.of("20.00", "GBP"));
    }

}
