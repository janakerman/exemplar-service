package com.janakerman.exemplarservice.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.math.BigDecimal;

import org.junit.Test;

public class PaymentTests {

    @Test
    public void paymentUpdatedFromPartialPaymentUpdatesFields() {
        Payment original = Payment.builder()
            .id("id1")
            .organisationId("org1")
            .amount(BigDecimal.valueOf(20.0))
            .build();

        Payment partial = Payment.builder()
            .id("should not be updated")
            .organisationId("org2")
            .amount(BigDecimal.valueOf(30.0))
            .build();

        Payment updated = original.updateFrom(partial);

        assertThat(updated.getId(), equalTo(original.getId()));
        assertThat(updated.getOrganisationId(), equalTo(partial.getOrganisationId()));
        assertThat(updated.getAmount(), equalTo(partial.getAmount()));
    }

}
