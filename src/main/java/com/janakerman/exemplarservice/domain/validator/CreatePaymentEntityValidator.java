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

        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) throw new PaymentValidationException();
    }
}
