package br.com.nossobancodigital.nossobanco.validators;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = AgeMajorityValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeMajority {
    String message() default "{date}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}