package com.jungko.jungko_server.keyword.validation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = KeywordCreateValidator.class)

public @interface KeywordCreateValidation {
	String message() default "KeywordCreateValidation";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}