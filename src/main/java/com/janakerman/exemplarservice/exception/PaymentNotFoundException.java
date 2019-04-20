package com.janakerman.exemplarservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Payment not found")
public class PaymentNotFoundException extends RuntimeException {}
