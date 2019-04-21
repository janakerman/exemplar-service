package com.janakerman.exemplarservice.domain;

import java.math.BigDecimal;

public interface IPayment {
    String getId();
    String getOrganisationId();
    BigDecimal getAmount();

    Payment updateFrom(Payment payment);
    boolean isValidToSave();
    boolean isValidToUpdate();
}
