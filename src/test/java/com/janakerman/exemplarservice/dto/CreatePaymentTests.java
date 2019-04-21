package com.janakerman.exemplarservice.dto;

import static java.util.UUID.fromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import java.math.BigDecimal;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class CreatePaymentTests {

    @Test
    public void toDomain() {
        CreatePayment createPayment = CreatePayment.builder()
            .organisationId("org1")
            .amount("20.00")
            .build();

        Payment domain = createPayment.toDomain();

        assertThat(fromString(domain.getId()), notNullValue());
        assertThat(domain.getOrganisationId(), equalTo(createPayment.getOrganisationId()));
        assertThat(domain.getAmount(), equalTo(new BigDecimal("20.00")));
    }

    @Test(expected = PaymentValidationException.class)
    public void toDomainNonNumericAmountThrows() {
        CreatePayment createPayment = CreatePayment.builder()
            .organisationId("org1")
            .amount("non numeric")
            .build();

        createPayment.toDomain();
    }
}
