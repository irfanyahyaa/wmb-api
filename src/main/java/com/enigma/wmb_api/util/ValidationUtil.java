package com.enigma.wmb_api.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
    private final Validator validator;
    private final EntityUtil entityUtil;

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> validate = validator.validate(object);

        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
    }

    public void validateSortFields(Class<?> comparator, String field) {
        if (!entityUtil.getValidSortFields(comparator).contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid sort field");
        }
    }

    public void validateEmptyData(Page<?> object, String data) {
        if (object.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, data + " not found");
        }
    }
}