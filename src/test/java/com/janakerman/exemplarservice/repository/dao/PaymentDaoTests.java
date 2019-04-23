package com.janakerman.exemplarservice.repository.dao;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;

public class PaymentDaoTests {

    @Test
    public void toDao() {
        Payment payment = Payment.builder()
            .id("id")
            .organisationId("org1")
            .amount(BigDecimal.ONE)
            .build();

        PaymentDao dao = PaymentDao.toDao(payment);

        assertThat(dao.getId(), equalTo("id"));
        assertThat(dao.getOrganisationId(), equalTo("org1"));
        assertThat(dao.getAmount(), equalTo("1"));
    }

    @Test
    public void toPayment() {
        PaymentDao dao = PaymentDao.builder()
            .id("id")
            .organisationId("org1")
            .amount("20.00")
            .build();

        Payment payment = dao.toPayment();

        assertThat(payment.getId(), equalTo(dao.getId()));
        assertThat(payment.getOrganisationId(), equalTo(dao.getOrganisationId()));
        assertThat(payment.getAmount(), equalTo(new BigDecimal(dao.getAmount())));
    }

}
