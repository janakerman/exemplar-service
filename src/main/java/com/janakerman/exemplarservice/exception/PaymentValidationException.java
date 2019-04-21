package com.janakerman.exemplarservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid payment request")
public class PaymentValidationException extends RuntimeException {}
