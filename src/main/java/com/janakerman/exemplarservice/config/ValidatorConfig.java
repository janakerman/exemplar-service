package com.janakerman.exemplarservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.janakerman.exemplarservice.domain.validator.CreatePaymentEntityValidator;
import com.janakerman.exemplarservice.domain.validator.CreatePaymentValidator;
import com.janakerman.exemplarservice.domain.validator.UpdatePaymentEntityValidator;
import com.janakerman.exemplarservice.domain.validator.UpdatePaymentValidator;

@Configuration
public class ValidatorConfig {

    @Bean
    CreatePaymentValidator createPaymentEntityValidator() {
        return new CreatePaymentEntityValidator();
    }

    @Bean
    UpdatePaymentValidator updatePaymentEntityValidator() {
        return new UpdatePaymentEntityValidator();
    }

}
