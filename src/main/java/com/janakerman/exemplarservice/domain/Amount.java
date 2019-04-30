package com.janakerman.exemplarservice.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Amount {

    private Currency currency;
    private BigDecimal amount;

    static public Amount of(String amount, String currency) {
        return Amount.builder()
                .currency(Currency.getInstance(currency))
                .amount(new BigDecimal(amount))
                .build();
    }

}
