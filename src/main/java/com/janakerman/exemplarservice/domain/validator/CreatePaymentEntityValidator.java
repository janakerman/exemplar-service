package com.janakerman.exemplarservice.domain.validator;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class CreatePaymentEntityValidator implements CreatePaymentValidator {
    @Override
    public void validate(Payment payment) {
        PaymentValidator.validateId(payment.getId());

        if (payment.getOrganisationId() == null) throw new PaymentValidationException();

        if (payment.getAmount() == null) throw new PaymentValidationException();
        PaymentValidator.validateAmount(payment.getAmount());
    }
}
