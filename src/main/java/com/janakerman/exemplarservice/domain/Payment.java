package com.janakerman.exemplarservice.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Payment implements IPayment {

    private String id;
    private String organisationId;
    private BigDecimal amount;

    @Override
    public Payment updateFrom(Payment payment) {
        return Payment.builder()
            .id(id)
            .organisationId(payment.getOrganisationId() != null ? payment.getOrganisationId() : organisationId)
            .amount(payment.getAmount() != null ? payment.getAmount() : amount)
            .build();
    }

}
