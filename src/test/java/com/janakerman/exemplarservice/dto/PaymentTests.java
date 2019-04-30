package com.janakerman.exemplarservice.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.util.Currency;

import com.janakerman.exemplarservice.domain.Amount;
import org.junit.Test;

public class PaymentTests {

    @Test
    public void fromDomain() {
        com.janakerman.exemplarservice.domain.Payment domain = com.janakerman.exemplarservice.domain.Payment.builder()
            .organisationId("org1")
            .amount(amount("20.00", "GBP"))
            .build();

        Payment dto = Payment.fromDomain(domain);
        assertThat(dto.getId(), equalTo(domain.getId()));
        assertThat(dto.getOrganisationId(), equalTo(domain.getOrganisationId()));
        assertThat(dto.getAmount().getValue(), equalTo("20.00"));
        assertThat(dto.getAmount().getCurrencyCode(), equalTo("GBP"));
    }

    @Test
    public void fromDomainAmountHasCurrencyDP_GBP() {
        com.janakerman.exemplarservice.domain.Payment domain = com.janakerman.exemplarservice.domain.Payment.builder()
            .amount(amount("20", "GBP"))
            .build();

        Payment dto = Payment.fromDomain(domain);
        assertThat(dto.getAmount().getValue(), equalTo("20.00"));
        assertThat(dto.getAmount().getCurrencyCode(), equalTo("GBP"));
    }

    @Test
    public void fromDomainAmountHasDPCurrencyDP_JOD() {
        com.janakerman.exemplarservice.domain.Payment domain = com.janakerman.exemplarservice.domain.Payment.builder()
                .amount(amount("30", "JOD"))
                .build();

        Payment dto = Payment.fromDomain(domain);
        assertThat(dto.getAmount().getValue(), equalTo("30.000"));
        assertThat(dto.getAmount().getCurrencyCode(), equalTo("JOD"));
    }

    private Amount amount(String amount, String currency) {
        return com.janakerman.exemplarservice.domain.Amount.builder()
                .amount(new BigDecimal(amount))
                .currency(Currency.getInstance(currency))
                .build();
    }

}
