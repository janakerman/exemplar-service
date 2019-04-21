package com.janakerman.exemplarservice.domain.validator;

import com.janakerman.exemplarservice.domain.Payment;

public interface CreatePaymentValidator {
    void validate(Payment payment);
}
