package com.janakerman.exemplarservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.repository.PaymentRepository;

@Component
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        // TODO: Validation of domain objects?
        paymentRepository.create(payment);
        return payment;
    }

    public Optional<Payment> getPayment(String id) {
        return Optional.ofNullable(paymentRepository.findById(id));
    }

    public void deletePayment(String id) {
        paymentRepository.delete(id);
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

}
