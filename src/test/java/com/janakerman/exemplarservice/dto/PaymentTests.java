package com.janakerman.exemplarservice.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.Test;

public class PaymentTests {

    @Test
    public void fromDomain() {
        com.janakerman.exemplarservice.domain.Payment domain = com.janakerman.exemplarservice.domain.Payment.builder()
            .organisationId("org1")
            .amount(new BigDecimal("20.00"))
            .build();

        Payment dto = Payment.fromDomain(domain);
        assertThat(dto.getId(), equalTo(domain.getId()));
        assertThat(dto.getOrganisationId(), equalTo(domain.getOrganisationId()));
        assertThat(dto.getAmount(), equalTo("20.00"));
    }

    @Test
    public void fromDomainAmountHasAtLeastTwo2DP() {
        com.janakerman.exemplarservice.domain.Payment domain = com.janakerman.exemplarservice.domain.Payment.builder()
            .amount(new BigDecimal("20"))
            .build();

        Payment dto = Payment.fromDomain(domain);
        assertThat(dto.getAmount(), equalTo("20.00"));
    }



}
