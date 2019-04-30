package com.janakerman.exemplarservice.dto;

import static java.util.UUID.fromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;

public class CreatePaymentTests {

    @Test
    public void toDomainWithValidValues() {
        CreatePayment createPayment = CreatePayment.builder()
            .amount(amount("20.00", "GBP"))
            .organisationId("org1")
            .build();

        Payment domain = createPayment.toDomain();

        assertThat(fromString(domain.getId()), notNullValue());
        assertThat(domain.getOrganisationId(), equalTo(createPayment.getOrganisationId()));
        assertThat(domain.getAmount().getAmount(), equalTo(new BigDecimal("20.00")));
        assertThat(domain.getAmount().getCurrency(), equalTo(Currency.getInstance("GBP")));
    }

    private static Amount amount(String value, String currencyCode) {
        return Amount.builder()
                .value(value)
                .currencyCode(currencyCode).
                        build();
    }
}
