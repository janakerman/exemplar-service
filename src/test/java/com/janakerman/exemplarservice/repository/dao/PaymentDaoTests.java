package com.janakerman.exemplarservice.repository.dao;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

import com.janakerman.exemplarservice.domain.Amount;
import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;

public class PaymentDaoTests {

    @Test
    public void toDao() {
        Payment payment = Payment.builder()
            .id("id")
            .organisationId("org1")
            .amount(Amount.of("1", "GBP"))
            .build();

        PaymentDao dao = PaymentDao.toDao(payment);

        assertThat(dao.getId(), equalTo("id"));
        assertThat(dao.getOrganisationId(), equalTo("org1"));
        assertThat(dao.getAmount().getAmount(), equalTo(BigDecimal.ONE));
        assertThat(dao.getAmount().getCurrency(), equalTo(Currency.getInstance("GBP")));
    }

    @Test
    public void toPayment() {
        PaymentDao dao = PaymentDao.builder()
            .id("id")
            .organisationId("org1")
            .amount(Amount.of("20.00", "GBP"))
            .build();

        Payment payment = dao.toPayment();

        assertThat(payment.getId(), equalTo(dao.getId()));
        assertThat(payment.getOrganisationId(), equalTo(dao.getOrganisationId()));
        assertThat(payment.getAmount(), equalTo(dao.getAmount()));
    }

}
