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
    private final PaymentValidationService paymentValidationService;

    @Autowired
    public PaymentService(
        PaymentRepository paymentRepository,
        PaymentValidationService paymentValidationService) {
        this.paymentRepository = paymentRepository;
        this.paymentValidationService = paymentValidationService;
    }

    public Payment createPayment(Payment payment) {
        paymentValidationService.validateCreatePayment(payment);
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Payment payment) {
        paymentValidationService.validateUpdatePayment(payment);

        Payment old = paymentRepository.findById(payment.getId());
        Payment updated = old.updateFrom(payment);

        return paymentRepository.save(updated);
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
