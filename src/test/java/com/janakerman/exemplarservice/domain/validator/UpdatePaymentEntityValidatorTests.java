package com.janakerman.exemplarservice.domain.validator;

import java.util.UUID;

import com.janakerman.exemplarservice.domain.Amount;
import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class UpdatePaymentEntityValidatorTests {

    private final UpdatePaymentEntityValidator updatePaymentEntityValidator = new UpdatePaymentEntityValidator();

    @Test
    public void validateUpdatePaymentAllFields() {
        Payment payment = validPayment()
                .organisationId("orgId")
                .amount(Amount.of("20.00", "GBP"))
                .build();
        updatePaymentEntityValidator.validate(payment);
    }

    @Test
    public void validateUpdatePaymentAdditionalFieldsOptional() {
        Payment payment = validPayment().build();
        updatePaymentEntityValidator.validate(payment);
    }

    @Test(expected = PaymentValidationException.class)
    public void validateCreatePaymentHasValidUUID() {
        Payment payment = validPayment()
                .id("not a uuid")
                .build();
        updatePaymentEntityValidator.validate(payment);
    }

    private Payment.PaymentBuilder validPayment() {
        return Payment.builder()
            .id(UUID.randomUUID().toString());
    }

}
