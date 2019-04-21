package com.janakerman.exemplarservice.dto;

import java.text.DecimalFormat;

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
    private String amount;

    static public Payment fromDomain(com.janakerman.exemplarservice.domain.Payment payment) {
        String formattedAmount = null;
        if (payment.getAmount() != null) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setGroupingUsed(false);
            formattedAmount = df.format(payment.getAmount());
        }

        return Payment.builder()
            .id(payment.getId())
            .organisationId(payment.getOrganisationId())
            .amount(formattedAmount)
            .build();
    }
}
