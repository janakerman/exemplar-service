package com.janakerman.exemplarservice.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Payment {
    private String id;
    private String organisationId;
    private Amount amount;

    static public Payment fromDomain(com.janakerman.exemplarservice.domain.Payment payment) {
        return Payment.builder()
            .id(payment.getId())
            .organisationId(payment.getOrganisationId())
            .amount(Amount.fromDomain(payment.getAmount()))
            .build();
    }
}
