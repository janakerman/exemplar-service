package com.janakerman.exemplarservice.dto;

import java.util.UUID;

import com.janakerman.exemplarservice.domain.Payment;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CreatePayment {

    private String organisationId;
    private Amount amount;

    public Payment toDomain() {
        return Payment.builder()
                    .id(UUID.randomUUID().toString())
                    .organisationId(organisationId)
                    .amount(amount != null ? amount.toDomain() : null)
                    .build();
    }

}
