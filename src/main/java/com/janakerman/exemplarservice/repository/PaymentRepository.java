package com.janakerman.exemplarservice.repository;

import java.util.List;

import com.janakerman.exemplarservice.domain.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
    Payment findById(String id);
    List<Payment> findAll();
    void delete(String id);
    void deleteAll();

}
