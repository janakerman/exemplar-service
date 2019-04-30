package com.janakerman.exemplarservice.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

public class PaymentTests {

    @Test
    public void paymentUpdatedFromPartialPaymentUpdatesFields() {
        Payment original = Payment.builder()
            .id("id1")
            .organisationId("org1")
            .amount(Amount.of("20.00", "GBP"))
            .build();

        Payment partial = Payment.builder()
            .id("should not be updated")
            .organisationId("org2")
            .amount(Amount.of("30.00", "GBP"))
            .build();

        Payment updated = original.updateFrom(partial);

        assertThat(updated.getId(), equalTo(original.getId()));
        assertThat(updated.getOrganisationId(), equalTo(partial.getOrganisationId()));
        assertThat(updated.getAmount(), equalTo(partial.getAmount()));
    }

}
