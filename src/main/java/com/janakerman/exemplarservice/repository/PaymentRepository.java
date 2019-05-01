package com.janakerman.exemplarservice.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.janakerman.exemplarservice.repository.entity.PaymentEntity;

@EnableScan
public interface PaymentRepository extends CrudRepository<PaymentEntity, String> {

}
