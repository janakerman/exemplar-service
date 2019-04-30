package com.janakerman.exemplarservice.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;

public class UpdatePaymentTests {

    @Test
    public void toDomainWithAllProperties() {
        UpdatePayment updatePayment = UpdatePayment.builder()
            .id("id")
            .organisationId("org1")
            .amount(Amount.builder()
                    .value("20.00")
                    .currencyCode("GBP")
                    .build())
            .build();

        Payment domain = updatePayment.toDomain();

        assertThat(domain.getId(), equalTo(updatePayment.getId()));
        assertThat(domain.getOrganisationId(), equalTo(updatePayment.getOrganisationId()));
        assertThat(domain.getAmount().getAmount(), equalTo(new BigDecimal("20.00")));
        assertThat(domain.getAmount().getCurrency(), equalTo(Currency.getInstance("GBP")));
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
}
