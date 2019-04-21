package com.janakerman.exemplarservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment implements IPayment {

    private String id;
    private String organisationId;

    @Override
    public Payment updateFrom(Payment payment) {
        return Payment.builder()
            .id(id)
            .organisationId(payment.getOrganisationId() != null ? payment.getOrganisationId() : organisationId)
            .build();
    }

    @Override
    public boolean isValidToSave() {
        if (id == null || organisationId == null) return false;
        return true;
    }

    @Override
    public boolean isValidToUpdate() {
        if (id == null) return false;
        return true;
    }



}
