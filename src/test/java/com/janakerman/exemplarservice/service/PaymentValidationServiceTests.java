package com.janakerman.exemplarservice.service;

import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janakerman.exemplarservice.domain.Payment;
import com.janakerman.exemplarservice.domain.validator.CreatePaymentValidator;
import com.janakerman.exemplarservice.domain.validator.UpdatePaymentValidator;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PaymentValidationServiceTests {

    private PaymentValidationService validationService;

    @MockBean CreatePaymentValidator createPaymentValidator;
    @MockBean UpdatePaymentValidator updatePaymentValidator;

    @Before
    public void setUp() {
        validationService = new PaymentValidationService(
            Collections.singletonList(createPaymentValidator),
            Collections.singletonList(updatePaymentValidator)
        );
    }

    @Test
    public void createValidationCallsAllValidator() {
        Payment payment = new Payment();
        validationService.validateCreatePayment(payment);
        verify(createPaymentValidator).validate(payment);
    }

    @Test
    public void updateValidationCallsAllValidator() {
        Payment payment = new Payment();
        validationService.validateUpdatePayment(payment);
        verify(updatePaymentValidator).validate(payment);
    }

}
