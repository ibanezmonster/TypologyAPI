package com.typology.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerValueValidator implements ConstraintValidator<ValidOrderedTritypeValues, Integer> {

    private int[] allowedValues;

    @Override
    public void initialize(ValidOrderedTritypeValues constraintAnnotation) {
        allowedValues = constraintAnnotation.allowedValues();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }
        for (int allowedValue : allowedValues) {
            if (value == allowedValue) {
                return true;
            }
        }
        return false;
    }
}