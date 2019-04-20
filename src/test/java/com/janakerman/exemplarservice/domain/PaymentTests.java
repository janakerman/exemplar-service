package com.janakerman.exemplarservice.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.janakerman.exemplarservice.dto.Payment;

public class PaymentTests {

    @Test
    public void fromDomain() {

        com.janakerman.exemplarservice.domain.Payment domain = com.janakerman.exemplarservice.domain.Payment.builder()
            .organisationId("org1")
            .build();

        Payment dto = Payment.fromDomain(domain);

        assertThat(dto.getId(), equalTo(domain.getId()));
        assertThat(dto.getOrganisationId(), equalTo(domain.getOrganisationId()));
    }

}
