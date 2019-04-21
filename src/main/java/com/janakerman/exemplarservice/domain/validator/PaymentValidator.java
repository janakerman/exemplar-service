package com.janakerman.exemplarservice.domain.validator;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

import java.math.BigDecimal;

import static java.util.UUID.fromString;

public interface PaymentValidator {

    void validate(Payment payment);

    static void validateId(String id) {
        if (id == null) {
            throw new PaymentValidationException();
        }
        try {
            fromString(id);
        } catch (IllegalArgumentException e) {
            throw new PaymentValidationException();
        }
    }

    static void validateAmount(BigDecimal amount) {
        if (amount != null &&
                (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.scale() > 2)) {
            throw new PaymentValidationException();
        }
    }
}
