package com.test.users.utils.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Formato de clave incorrecto";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
