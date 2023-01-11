package com.boker.mpesa.Application;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice

public class RequestException {
//    @ExceptionHandler(RestClientResponseException.class)
//    public List<String>requestErrors(RestClientResponseException restClientResponseException){
//        List<String>errors=new ArrayList<>();
//        errors.forEach(s -> restClientResponseException.getMessage());
//        return errors;
//    }
}
