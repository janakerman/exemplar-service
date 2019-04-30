package com.janakerman.exemplarservice.dto;

import com.janakerman.exemplarservice.exception.PaymentValidationException;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Amount {

    private String value;
    private String currencyCode;

    com.janakerman.exemplarservice.domain.Amount toDomain() {
        BigDecimal amount;
        try {
            amount = new BigDecimal(this.value);
        } catch (Exception e) {
            throw new PaymentValidationException();
        }

        Currency currency;
        try {
            currency = Currency.getInstance(currencyCode);
        } catch (Exception e) {
            throw new PaymentValidationException();
        }

        try {
            return com.janakerman.exemplarservice.domain.Amount.builder()
                    .currency(currency)
                    .amount(amount)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new PaymentValidationException();
        }
    }

    static Amount fromDomain(com.janakerman.exemplarservice.domain.Amount amount) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMinimumFractionDigits(amount.getCurrency().getDefaultFractionDigits());
        df.setMaximumFractionDigits(amount.getCurrency().getDefaultFractionDigits());
        df.setRoundingMode(RoundingMode.HALF_UP);
        String formattedAmount = df.format(amount.getAmount());

        return Amount.builder()
                .value(formattedAmount)
                .currencyCode(amount.getCurrency().toString())
                .build();
    }
}
