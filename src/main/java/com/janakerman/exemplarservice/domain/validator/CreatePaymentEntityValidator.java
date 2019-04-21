package com.janakerman.exemplarservice.domain.validator;

import java.math.BigDecimal;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class CreatePaymentEntityValidator implements CreatePaymentValidator {
    @Override
    public void validate(Payment payment) {
        if (payment.getId() == null ||
            payment.getOrganisationId() == null ||
            payment.getAmount() == null) {
            throw new PaymentValidationException();
        }

        BigDecimal amount = payment.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.scale() > 2) {
            throw new PaymentValidationException();
        }
    }
}
