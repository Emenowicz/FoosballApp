package com.michalowicz.inzynierka.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class OneOfValidator implements ConstraintValidator<OneOf, Integer> {

    private int[] array;

    @Override
    public boolean isValid(final Integer integer, final ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(array).anyMatch(i -> i == integer);
    }

    @Override
    public void initialize(final OneOf constraintAnnotation) {
        array = constraintAnnotation.value();
    }
}
