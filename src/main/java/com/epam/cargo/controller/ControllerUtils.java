package com.epam.cargo.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.UNKNOWN_ERROR_KEY_MESSAGE;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult, ResourceBundle bundle) {
        Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "ErrorMessage",
                fieldError -> bundle.getString(Optional.ofNullable(fieldError.getDefaultMessage()).orElse(UNKNOWN_ERROR_KEY_MESSAGE))
        );
        return bindingResult.getFieldErrors()
                .stream()
                .collect(fieldErrorMapCollector
                );
    }
}
