package com.alibou.library.handler;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface CustomEmail {
    String message() default "Email should be in a proper format with.com or .co";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
