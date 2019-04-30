package com.janakerman.exemplarservice.dto;

import com.janakerman.exemplarservice.exception.PaymentValidationException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AmountTests {

    @Test
    public void toDomainWithValidNumber() {
        amount("20.00", "GBP");
    }


    @Test(expected = PaymentValidationException.class)
    public void toDomainNonNumericAmountThrows() {
        amount("not a number", "GBP").toDomain();

    }

    @Test(expected = PaymentValidationException.class)
    public void toDomainNonExistantCurrencyThrows() {
        amount("20.00", "ZZZZZ").toDomain();
    }

    @Test(expected = PaymentValidationException.class)
    public void toDomainNullAmountThrows() {
        amount(null, "GBP").toDomain();

    }

    @Test(expected = PaymentValidationException.class)
    public void toDomainNullCurrencyThrows() {
        amount("20.00", null).toDomain();

    }

    @Test
    public void fromDomainReturnsRoundingToCurrencyDefault_GBP_Down() {
        Amount amount = Amount.fromDomain(com.janakerman.exemplarservice.domain.Amount.of("20.000001", "GBP"));
        assertThat(amount.getValue(), equalTo("20.00"));
        assertThat(amount.getCurrencyCode(), equalTo("GBP"));
    }

    @Test
    public void fromDomainReturnsRoundingToCurrencyDefault_GBP_UP() {
        Amount amount = Amount.fromDomain(com.janakerman.exemplarservice.domain.Amount.of("20.005", "GBP"));
        assertThat(amount.getValue(), equalTo("20.01"));
        assertThat(amount.getCurrencyCode(), equalTo("GBP"));
    }

    @Test
    public void fromDomainReturnsRoundingToCurrencyDefault_JOD_UP() {
        Amount amount = Amount.fromDomain(com.janakerman.exemplarservice.domain.Amount.of("20.0055", "JOD"));
        assertThat(amount.getValue(), equalTo("20.006"));
        assertThat(amount.getCurrencyCode(), equalTo("JOD"));
    }

    private static Amount amount(String value, String currencyCode) {
        return Amount.builder()
                .value(value)
                .currencyCode(currencyCode)
                .build();
    }

}
