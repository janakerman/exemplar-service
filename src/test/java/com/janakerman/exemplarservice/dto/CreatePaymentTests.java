package com.janakerman.exemplarservice.dto;

import static java.util.UUID.fromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import com.janakerman.exemplarservice.domain.Payment;

public class CreatePaymentTests {

    @Test
    public void toDomain() {
        CreatePayment createPayment = CreatePayment.builder()
            .organisationId("org1")
            .build();

        Payment domain = createPayment.toDomain();

        assertThat(fromString(domain.getId()), notNullValue());
        assertThat(domain.getOrganisationId(), equalTo(createPayment.getOrganisationId()));
    }
}
