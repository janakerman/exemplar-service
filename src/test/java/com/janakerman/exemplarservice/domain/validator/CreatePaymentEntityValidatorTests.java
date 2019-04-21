package com.janakerman.exemplarservice.domain.validator;

import java.math.BigDecimal;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class CreatePaymentEntityValidatorTests {

    private final CreatePaymentEntityValidator createPaymentEntityValidator = new CreatePaymentEntityValidator();

    @Test
    public void validateCreatePayment() {
        Payment payment = validPayment()
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
    public void validateCreatePaymentNegativePaymentsInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(-1))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentZeroPaymentInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(0))
            .build();
        createPaymentEntityValidator.validate(payment);
    }

    private Payment.PaymentBuilder validPayment() {
        return Payment.builder()
            .id("id")
            .organisationId("organsationId")
            .amount(new BigDecimal(20.0));
    }

}
