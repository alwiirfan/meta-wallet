package com.enigma.metawallet.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final Validator validator;

    public <T> void validate(T obj){
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        if (!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
