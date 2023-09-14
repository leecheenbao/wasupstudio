package com.wasupstudio.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuestionValidator.class)
public @interface ValidQuestion {
    String message() default "Invalid input. Only A, B, C, or D are allowed.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
