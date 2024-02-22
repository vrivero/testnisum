package com.test.users.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Value("${email.regex}")
    private String EMAIL_REGEX;

    @Override
    public void initialize(Email password) {
    }

    @Override
    public boolean isValid(String emailField,
                           ConstraintValidatorContext cxt) {
        return emailField != null && emailField.matches(EMAIL_REGEX)
                && (emailField.length() >= 8);
    }

}