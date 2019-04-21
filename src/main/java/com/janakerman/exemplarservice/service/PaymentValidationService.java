package com.janakerman.exemplarservice.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.domain.validator.CreatePaymentValidator;
import com.janakerman.exemplarservice.domain.validator.UpdatePaymentValidator;

@Component
public class PaymentValidationService {

    private final List<CreatePaymentValidator> createPaymentValidators;
    private final List<UpdatePaymentValidator> updatePaymentValidators;

    public PaymentValidationService(List<CreatePaymentValidator> createPaymentValidators, List<UpdatePaymentValidator> updatePaymentValidators) {
        this.createPaymentValidators = createPaymentValidators;
        this.updatePaymentValidators = updatePaymentValidators;
    }

    void validateCreatePayment(Payment payment) {
        createPaymentValidators.forEach(validator-> validator.validate(payment));
    }

    void validateUpdatePayment(Payment payment) {
        updatePaymentValidators.forEach(validator -> validator.validate(payment));
    }

}
