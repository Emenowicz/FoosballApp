package com.michalowicz.inzynierka.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OneOfValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;
    @Mock
    OneOf oneOf;
    private int[] array = {2, 4, 6, 8};

    @Test
    public void shouldValidateCorrectly() {
//      given
        when(oneOf.value()).thenReturn(array);
        OneOfValidator validator = new OneOfValidator();
        validator.initialize(oneOf);
//      when
        boolean result = validator.isValid(4, constraintValidatorContext);
//      then
        assertTrue(result);
    }

    @Test
    public void shouldValidateFail() {
//      given
        when(oneOf.value()).thenReturn(array);
        OneOfValidator validator = new OneOfValidator();
        validator.initialize(oneOf);
//      when
        boolean result = validator.isValid(3, constraintValidatorContext);
//      then
        assertFalse(result);
    }

}