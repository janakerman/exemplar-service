package com.janakerman.exemplarservice.dto;

import java.util.UUID;

import com.janakerman.exemplarservice.domain.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePayment {

    private String organisationId;

    public com.janakerman.exemplarservice.domain.Payment toDomain() {
        return Payment.builder()
            .id(UUID.randomUUID().toString())
            .organisationId(organisationId)
            .build();
    }

}
