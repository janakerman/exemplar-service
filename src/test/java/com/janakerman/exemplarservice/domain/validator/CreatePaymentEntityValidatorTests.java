package com.janakerman.exemplarservice.domain.validator;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class CreatePaymentEntityValidatorTests {

    private final CreatePaymentEntityValidator createPaymentEntityValidator = new CreatePaymentEntityValidator();

    @Test
    public void validateCreatePayment2DPAmountValid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(20.00))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test
    public void validateCreatePaymentIntegerAmountValid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(20))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentIdRequired() {
        Payment payment = Payment.builder()
            .organisationId("organisationId")
            .amount(new BigDecimal(20))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentOrganisationIdRequired() {
        Payment payment = Payment.builder()
            .id("id")
            .amount(new BigDecimal(20))
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

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentNegativeAmountInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(-1))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentZeroAmountInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(0))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentMoreThan2DPAmountInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(2.001))
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

    private Payment.PaymentBuilder validPayment() {
        return Payment.builder()
            .id(UUID.randomUUID().toString())
            .organisationId("organsationId")
            .amount(new BigDecimal(20.00));
    }

}
