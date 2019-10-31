package org.softuni.residentevil.web.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = CreatorFormatValidator.class)
public @interface CreatorFormat {
    String message() default "Creator must be 'Corp' or 'corp'! ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
