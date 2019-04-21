package com.janakerman.exemplarservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

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
    private String amount;

    public com.janakerman.exemplarservice.domain.Payment toDomain() {
        BigDecimal amount;
        try {
            amount = new BigDecimal(this.amount);
        } catch (Exception e) {
            throw new PaymentValidationException();
        }

        return Payment.builder()
            .id(UUID.randomUUID().toString())
            .organisationId(organisationId)
            .amount(amount)
            .build();
    }

}
