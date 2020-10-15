package pl.polsl.workflow.manager.server.helper.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankIfExistsValidator implements ConstraintValidator<NotBlankIfExists, CharSequence> {

    @Override
    public void initialize(NotBlankIfExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null)
            return true;
        else
            return !StringUtils.trimAllWhitespace(value.toString()).isEmpty();
    }

}
