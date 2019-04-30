package com.janakerman.exemplarservice.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.janakerman.exemplarservice.domain.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentNotFoundException;
import com.janakerman.exemplarservice.repository.PaymentRepository;
import com.janakerman.exemplarservice.repository.dao.PaymentDao;

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
            .amount(Amount.of("20.00", "GBP"))
            .build();

        when(paymentRepository.save(any())).thenReturn(PaymentDao.toDao(payment));

        Payment retPayment = paymentService.createPayment(payment);

        ArgumentCaptor<PaymentDao> argumentCaptor = ArgumentCaptor.forClass(PaymentDao.class);
        verify(paymentRepository).save(argumentCaptor.capture());

        PaymentDao savedPayment = argumentCaptor.getValue();

        assertThat(savedPayment, equalTo(PaymentDao.toDao(payment)));

        assertThat(retPayment, equalTo(payment));
    }

    @Test
    public void getPayment() {
        Payment payment = Payment.builder()
            .id("id")
            .organisationId("org")
            .amount(Amount.of("20.00", "GBP"))
            .build();

        when(paymentRepository.findById(payment.getId()))
            .thenReturn(Optional.of(PaymentDao.toDao(payment)));

        Payment retPayment = paymentService.getPayment(payment.getId());
        assertThat(retPayment, equalTo(payment));
    }

    @Test(expected = PaymentNotFoundException.class)
    public void getPaymentNonExistantId() {
        String id = "test id";

        when(paymentRepository.findById(id))
            .thenReturn(Optional.empty());

        paymentService.getPayment(id);
    }

    @Test
    public void getPayments() {
        Payment payment1 = Payment.builder()
            .id("1")
            .organisationId("org1")
            .amount(Amount.of("1", "GBP"))
            .build();
        Payment payment2 = Payment.builder()
            .id("2")
            .organisationId("org2")
            .amount(Amount.of("1", "GBP"))
            .build();

        List<Payment> paymentList = Arrays.asList(payment1, payment2);
        List<PaymentDao> paymentDaoList = Arrays.asList(PaymentDao.toDao(payment1), PaymentDao.toDao(payment2));

        when(paymentRepository.findAll())
            .thenReturn(paymentDaoList );

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
            .amount(Amount.of("1", "GBP"))
            .build();

        when(paymentRepository.findById(updatedPayment.getId()))
            .thenReturn(Optional.of(PaymentDao.toDao(oldPayment)));

        when(paymentRepository.save(any()))
            .thenAnswer(invocation -> invocation.getArgument(0));

        paymentService.updatePayment(updatedPayment);

        ArgumentCaptor<PaymentDao> argumentCaptor = ArgumentCaptor.forClass(PaymentDao.class);
        verify(paymentRepository).save(argumentCaptor.capture());
        PaymentDao savedPayment = argumentCaptor.getValue();

        assertThat(savedPayment.getOrganisationId(), equalTo(updatedPayment.getOrganisationId()));
    }

    @Test(expected = PaymentNotFoundException.class)
    public void updatePaymentNotExistant() {
        Payment updatedPayment = Payment.builder()
            .id("1")
            .organisationId("new org")
            .amount(Amount.of("1", "GBP"))
            .build();

        when(paymentRepository.findById(updatedPayment.getId()))
            .thenReturn(Optional.empty());

        paymentService.updatePayment(updatedPayment);
    }

    @Test
    public void deletePayment() {
        String id = "test id";
        paymentService.deletePayment(id);
        verify(paymentRepository).deleteById(eq(id));
    }

}
