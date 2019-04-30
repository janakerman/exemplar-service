package com.janakerman.exemplarservice.repository.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.janakerman.exemplarservice.domain.Amount;
import com.janakerman.exemplarservice.domain.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = "payments")
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentDao {
    private String id;
    private String organisationId;
    private Amount amount;

    @DynamoDBHashKey
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getOrganisationId() {
        return organisationId;
    }

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson
    public Amount getAmount() {
        return amount;
    }

    static public PaymentDao toDao(Payment payment) {
        return PaymentDao.builder()
            .id(payment.getId())
            .organisationId(payment.getOrganisationId())
            .amount(payment.getAmount())
            .build();
    }

    public Payment toPayment() {
        return Payment.builder()
            .id(this.getId())
            .organisationId(this.getOrganisationId())
            .amount(this.getAmount())
            .build();
    }
}
