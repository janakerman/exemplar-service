package com.janakerman.exemplarservice.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentNotFoundException;
import com.janakerman.exemplarservice.repository.PaymentRepository;
import com.janakerman.exemplarservice.repository.dao.PaymentDao;

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
        return paymentRepository.save(PaymentDao.toDao(payment))
            .toPayment();
    }

    public Payment updatePayment(Payment payment) {
        paymentValidationService.validateUpdatePayment(payment);

        Payment old = paymentRepository.findById(payment.getId())
            .orElseThrow(PaymentNotFoundException::new)
            .toPayment();

        Payment updated = old.updateFrom(payment);

        return paymentRepository.save(PaymentDao.toDao(updated))
            .toPayment();
    }

    public Payment getPayment(String id) {
        return paymentRepository.findById(id)
            .orElseThrow(PaymentNotFoundException::new)
            .toPayment();
    }

    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }

    public List<Payment> getPayments() {
        return StreamSupport.stream(paymentRepository.findAll().spliterator(), false)
            .map(PaymentDao::toPayment)
            .collect(Collectors.toList());
    }

}
