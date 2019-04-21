package com.janakerman.exemplarservice.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.janakerman.exemplarservice.domain.Payment;

@Component
public class InMemoryPaymentRepository implements PaymentRepository {

    private Map<String, Payment> store = new HashMap<>();

    @Override
    public Payment save(Payment payment) {
        store.put(payment.getId(), payment);
        return payment;
    }

    @Override
    public Payment findById(String id) {
        return store.get(id);
    }

    @Override
    public List<Payment> findAll() {
        return new LinkedList<>(store.values());
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }

    @Override
    public void deleteAll() {
        store = new HashMap<>();
    }

}
