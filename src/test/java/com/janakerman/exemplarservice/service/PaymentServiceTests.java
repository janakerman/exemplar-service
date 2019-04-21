package com.janakerman.exemplarservice.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.repository.PaymentRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PaymentServiceTests {

    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    PaymentValidationService paymentValidationService;

    private PaymentService paymentService;

    @Before
    public void setUp() {
        paymentService = new PaymentService(paymentRepository, paymentValidationService);
    }

    @Test
    public void createPayment() {
        Payment payment = Payment.builder()
            .id("id")
            .organisationId("org")
            .amount(new BigDecimal("20.00"))
            .build();

        when(paymentRepository.save(eq(payment))).thenReturn(payment);

        Payment retPayment = paymentService.createPayment(payment);

        ArgumentCaptor<Payment> argumentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(argumentCaptor.capture());

        Payment savedPayment = argumentCaptor.getValue();

        assertThat(savedPayment, equalTo(payment));
        assertThat(retPayment, equalTo(payment));
    }

    @Test
    public void getPayment() {
        Payment payment = Payment.builder()
            .id("id")
            .organisationId("org")
            .build();

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Optional<Payment> retPayment = paymentService.getPayment(payment.getId());
        assertThat(retPayment.get(), equalTo(payment));
    }

    @Test
    public void getPaymentNonExistantId() {
        String id = "test id";

        when(paymentRepository.findById(id)).thenReturn(null);

        Optional<Payment> retPayment = paymentService.getPayment(id);
        assertThat(retPayment.isPresent(), equalTo(false));
    }

    @Test
    public void getPayments() {
        Payment payment1 = Payment.builder()
            .id("1")
            .organisationId("org1")
            .build();
        Payment payment2 = Payment.builder()
            .id("2")
            .organisationId("org2")
            .build();
        List<Payment> paymentList = Arrays.asList(payment1, payment2);

        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> retPayments = paymentService.getPayments();
        assertThat(retPayments, equalTo(paymentList));
    }

    @Test
    public void updatePayment() {
        Payment updatedPayment = Payment.builder()
            .id("1")
            .organisationId("new org")
            .build();

        Payment oldPayment = Payment.builder()
            .id("1")
            .organisationId("old org")
            .build();

        when(paymentRepository.findById(updatedPayment.getId())).thenReturn(oldPayment);

        paymentService.updatePayment(updatedPayment);

        ArgumentCaptor<Payment> argumentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(argumentCaptor.capture());
        Payment savedPayment = argumentCaptor.getValue();

        assertThat(savedPayment.getOrganisationId(), equalTo(updatedPayment.getOrganisationId()));
    }

    @Test
    public void deletePayment() {
        String id = "test id";
        paymentService.deletePayment(id);
        verify(paymentRepository).delete(eq(id));
    }

}
