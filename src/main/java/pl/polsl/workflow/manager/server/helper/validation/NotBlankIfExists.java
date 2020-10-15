package pl.polsl.workflow.manager.server.helper.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NotBlankIfExistsValidator.class)
public @interface NotBlankIfExists {
    String message() default "must not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}