package com.janakerman.exemplarservice.domain.validator;

import com.janakerman.exemplarservice.domain.Payment;

public class UpdatePaymentEntityValidator implements UpdatePaymentValidator {

    @Override
    public void validate(Payment payment) {
        PaymentValidator.validateId(payment.getId());

        if (payment.getAmount() != null) {
            PaymentValidator.validateAmount(payment.getAmount());
        }
    }
}
