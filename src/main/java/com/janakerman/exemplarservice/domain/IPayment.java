package com.janakerman.exemplarservice.domain;

import java.math.BigDecimal;

public interface IPayment {
    String getId();
    String getOrganisationId();
    Amount getAmount();

    Payment updateFrom(Payment payment);
}
