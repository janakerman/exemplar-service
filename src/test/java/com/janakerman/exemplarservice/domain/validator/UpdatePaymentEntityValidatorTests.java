package com.janakerman.exemplarservice.domain.validator;

import java.math.BigDecimal;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class UpdatePaymentEntityValidatorTests {

    private final UpdatePaymentEntityValidator updatePaymentEntityValidator = new UpdatePaymentEntityValidator();

    @Test
    public void validateUpdatePaymentAdditionalFieldsOptional() {
        Payment payment = validPayment().build();
        updatePaymentEntityValidator.validate(payment);
    }

    @Test
    public void validateUpdatePayment2DPAmountValid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(20.00))
            .build();
        updatePaymentEntityValidator.validate(payment);
    }

    @Test
    public void validateUpdatePaymentIntegerAmountValid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(20))
            .build();
        updatePaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateUpdatePaymentNegativePaymentsInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(-1))
            .build();
        updatePaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateUpdatePaymentZeroPaymentInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(0))
            .build();
        updatePaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateUpdatePaymentMoreThan2DPAmountInvalid() {
        Payment payment = validPayment()
            .amount(new BigDecimal(2.001))
            .build();
        updatePaymentEntityValidator.validate(payment);
    }

    private Payment.PaymentBuilder validPayment() {
        return Payment.builder()
            .id("id");
    }

}
