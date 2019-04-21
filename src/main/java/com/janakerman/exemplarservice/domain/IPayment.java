package com.janakerman.exemplarservice.domain;

public interface IPayment {
    String getId();
    String getOrganisationId();

    Payment updateFrom(Payment payment);
    boolean isValidToSave();
    boolean isValidToUpdate();
}
