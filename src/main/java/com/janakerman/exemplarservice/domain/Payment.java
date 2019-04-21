package com.janakerman.exemplarservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment  implements IPayment {
    private String id;
    private String organisationId;

    @Override
    public boolean isValidToSave() {
        return id != null && organisationId != null;
    }
}
