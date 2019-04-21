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

    private Payment.PaymentBuilder validPayment() {
        return Payment.builder()
            .id("id");
    }

}
