package com.test.users.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Value("${password.regex}")
    private String PASS_REGEX;

    @Override
    public void initialize(Password password) {
    }

    @Override
    public boolean isValid(String passwordField,
                           ConstraintValidatorContext cxt) {
        return passwordField != null && passwordField.matches(PASS_REGEX)
                && (passwordField.length() >= 8);
    }

}
