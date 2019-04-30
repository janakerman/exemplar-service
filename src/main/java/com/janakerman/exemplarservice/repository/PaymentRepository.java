package com.janakerman.exemplarservice.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.janakerman.exemplarservice.repository.dao.PaymentDao;

@EnableScan
public interface PaymentRepository extends CrudRepository<PaymentDao, String> {

}
