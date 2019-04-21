package com.janakerman.exemplarservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.exception.PaymentValidationException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdatePayment {
    String id;
    String organisationId;
    String amount;

    public com.janakerman.exemplarservice.domain.Payment toDomain() {

        BigDecimal amount = null;
        if (this.amount != null) {
            try {
                amount = new BigDecimal(this.amount);
            } catch (Exception e) {
                throw new PaymentValidationException();
            }
        }

        return Payment.builder()
            .id(id)
            .organisationId(organisationId)
            .amount(amount)
            .build();
    }
}
