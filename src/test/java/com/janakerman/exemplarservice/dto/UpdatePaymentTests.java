package com.janakerman.exemplarservice.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

import java.math.BigDecimal;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

public class UpdatePaymentTests {

    @Test
    public void toDomainWithAllProperties() {
        UpdatePayment updatePayment = UpdatePayment.builder()
            .id("id")
            .organisationId("org1")
            .amount("20.00")
            .build();

        Payment domain = updatePayment.toDomain();

        assertThat(domain.getId(), equalTo(updatePayment.getId()));
        assertThat(domain.getOrganisationId(), equalTo(updatePayment.getOrganisationId()));
        assertThat(domain.getAmount(), equalTo(new BigDecimal("20.00")));
    }

    @Test
    public void toDomainWithEmptyFields() {
        UpdatePayment updatePayment = UpdatePayment.builder()
            .id("id")
            .build();

        Payment domain = updatePayment.toDomain();

        assertThat(domain.getId(), equalTo(updatePayment.getId()));
        assertThat(domain.getOrganisationId(), nullValue());
        assertThat(domain.getAmount(), nullValue());
    }

    @Test(expected = PaymentValidationException.class)
    public void toDomainNonNumericAmountThrows() {
        UpdatePayment updatePayment = UpdatePayment.builder()
            .organisationId("org1")
            .amount("non numeric")
            .build();

        updatePayment.toDomain();
    }
}
