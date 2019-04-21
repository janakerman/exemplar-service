package com.janakerman.exemplarservice.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;

public class UpdatePaymentTests {

    @Test
    public void toDomain() {
        UpdatePayment updatePayment = UpdatePayment.builder()
            .id("id")
            .organisationId("org1")
            .build();

        Payment domain = updatePayment.toDomain();

        assertThat(domain.getId(), equalTo(updatePayment.getId()));
        assertThat(domain.getOrganisationId(), equalTo(updatePayment.getOrganisationId()));
    }
}
