package com.janakerman.exemplarservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.janakerman.exemplarservice.dto.CreatePayment;
import com.janakerman.exemplarservice.dto.Payment;
import com.janakerman.exemplarservice.dto.UpdatePayment;
import com.janakerman.exemplarservice.exception.PaymentNotFoundException;
import com.janakerman.exemplarservice.service.PaymentService;

@RestController("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments/{paymentId}")
    public Payment getPayment(@PathVariable(value = "paymentId") String paymentId) {
        return Payment.fromDomain(
            paymentService.getPayment(paymentId)
                .orElseThrow(PaymentNotFoundException::new)
        );
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return paymentService.getPayments()
            .stream()
            .map(Payment::fromDomain)
            .collect(Collectors.toList());
    }

    @PostMapping("/payments")
    public Payment createPayment(@RequestBody CreatePayment createPayment) {
        return Payment.fromDomain(
            paymentService.createPayment(
                createPayment.toDomain()
            )
        );
    }

    @PutMapping("/payments")
    public Payment updatePayment(@RequestBody UpdatePayment updatePayment) {
        return Payment.fromDomain(
            paymentService.createPayment(
                updatePayment.toDomain()
            )
        );
    }

    @PatchMapping("/payments")
    public Payment partialUpdatePayment(@RequestBody UpdatePayment updatePayment) {
        return Payment.fromDomain(
            paymentService.updatePayment(
                updatePayment.toDomain()
            )
        );
    }

    @DeleteMapping("/payments/{paymentId}")
    public void deletePayment(@PathVariable(value = "paymentId") String paymentId) {
        paymentService.deletePayment(paymentId);
    }

}
