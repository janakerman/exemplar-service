package com.janakerman.exemplarservice.domain;

import static java.util.UUID.fromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.UUID;

import org.junit.Test;

import com.janakerman.exemplarservice.dto.CreatePayment;

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
