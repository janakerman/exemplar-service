package com.janakerman.exemplarservice.dto;

import com.janakerman.exemplarservice.domain.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UpdatePayment {
    String id;
    String organisationId;
    Amount amount;

    public com.janakerman.exemplarservice.domain.Payment toDomain() {
        return Payment.builder()
            .id(id)
            .organisationId(organisationId)
            .amount(amount != null ? amount.toDomain() : null)
            .build();
    }
}
