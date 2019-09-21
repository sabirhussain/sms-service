package com.auzmor.sample.sms.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNumberValidator.class)
@Documented
public @interface UserNumber {
	String message() default "{number.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
