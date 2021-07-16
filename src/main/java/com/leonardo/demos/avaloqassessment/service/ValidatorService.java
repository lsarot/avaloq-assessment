package com.leonardo.demos.avaloqassessment.service;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidatorService {


    public List<String> validate(Object... objects) {

        List<String> violationMsgs = Collections.emptyList();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = new HashSet<>();

        for (Object o : objects) {
            violations.addAll(validator.validate(o));
        }

        if (violations.size() > 0) {
            violationMsgs = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());
        }

        return violationMsgs;
    }

}
