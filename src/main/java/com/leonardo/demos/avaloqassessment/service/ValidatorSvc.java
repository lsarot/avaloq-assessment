package com.leonardo.demos.avaloqassessment.service;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidatorSvc {


    public Set<String> validate(Object... objects) {

        Set<String> violationMsgs = Collections.emptySet();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<Object>> violations = new ArrayList<>();

        for (Object o : objects) {
            violations.addAll(validator.validate(o));
        }

        if (violations.size() > 0) {
            violationMsgs = violations.stream().map(v -> v.getMessage()).collect(Collectors.toSet());
        }

        return violationMsgs;
    }

}
