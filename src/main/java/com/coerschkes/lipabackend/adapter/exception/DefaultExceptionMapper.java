package com.coerschkes.lipabackend.adapter.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class DefaultExceptionMapper {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionMapper.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleEntityNotFoundException(final EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return mapWithMessage(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMeineAnwendungException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return mapWithMessage(ex.getMessage());
    }

    private Map<String, String> mapWithMessage(final String message) {
        return Map.of("message", message);
    }
}