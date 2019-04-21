package com.janakerman.exemplarservice.dto;

import java.util.UUID;

import com.janakerman.exemplarservice.domain.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdatePayment {
    String id;
    String organisationId;

    public com.janakerman.exemplarservice.domain.Payment toDomain() {
        return Payment.builder()
            .id(id)
            .organisationId(organisationId)
            .build();
    }
}
