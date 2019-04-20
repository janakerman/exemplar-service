package com.janakerman.exemplarservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private String id;
    private String organisationId;

    static public Payment fromDomain(com.janakerman.exemplarservice.domain.Payment payment) {
        return Payment.builder()
            .id(payment.getId())
            .organisationId(payment.getOrganisationId())
            .build();
    }
}
