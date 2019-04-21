package com.janakerman.exemplarservice.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

public class PaymentTests {

    @Test
    public void paymentValidateValidPayment() {
        Payment payment = Payment.builder()
            .id("id")
            .organisationId("organsationId")
            .build();
        assertThat(payment.isValidToSave(), equalTo(true));
    }

    @Test
    public void paymentValidateSaveWithoutIdIsInvalid() {
        Payment payment = Payment.builder()
            .organisationId("organsationId")
            .build();
        assertThat(payment.isValidToSave(), equalTo(false));
    }

    @Test
    public void paymentValidateSaveWithoutOrganisationIdIsInvalid() {
        Payment payment = Payment.builder()
            .id("id")
            .build();
        assertThat(payment.isValidToSave(), equalTo(false));
    }

    @Test
    public void paymentUpdatedFromPartialPaymentUpdatesFields() {
        Payment original = Payment.builder()
            .id("id1")
            .organisationId("org1")
            .build();

        Payment partial = Payment.builder()
            .id("should not be updated")
            .organisationId("org2")
            .build();

        Payment updated = original.updateFrom(partial);

        assertThat(updated.getId(), equalTo(original.getId()));
        assertThat(updated.getOrganisationId(), equalTo(partial.getOrganisationId()));
    }

}
