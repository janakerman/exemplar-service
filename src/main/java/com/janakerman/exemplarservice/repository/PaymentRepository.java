package com.janakerman.exemplarservice.repository;

import java.util.List;

import com.janakerman.exemplarservice.domain.Payment;

public interface PaymentRepository {

    Payment create(Payment payment);
    Payment findById(String id);
    List<Payment> findAll();
    void delete(String id);
    void deleteAll();

}
