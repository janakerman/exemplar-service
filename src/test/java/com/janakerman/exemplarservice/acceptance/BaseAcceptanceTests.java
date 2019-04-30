package com.janakerman.exemplarservice.acceptance;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.janakerman.exemplarservice.repository.PaymentRepository;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseAcceptanceTests {

    @LocalServerPort
    int port;


    @Autowired private PaymentRepository repository;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

}
