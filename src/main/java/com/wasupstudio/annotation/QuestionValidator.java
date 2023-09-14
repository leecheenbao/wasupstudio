package com.wasupstudio.annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuestionValidator implements ConstraintValidator<ValidQuestion, String> {
    @Override
    public void initialize(ValidQuestion constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 验证输入是否为 A、B、C 或 D
        return "A".equalsIgnoreCase(value) || "B".equalsIgnoreCase(value)
                || "C".equalsIgnoreCase(value) || "D".equalsIgnoreCase(value);
    }
}
