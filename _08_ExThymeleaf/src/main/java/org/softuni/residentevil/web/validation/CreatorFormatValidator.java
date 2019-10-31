package org.softuni.residentevil.web.validation;

import org.softuni.residentevil.domain.entities.Creator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreatorFormatValidator implements ConstraintValidator<CreatorFormat, Creator> {

    @Override
    public boolean isValid(Creator creatorName, ConstraintValidatorContext context) {
            if(creatorName == null  ) {
                return false;
            } else if(!Creator.Corp.toString().equals(creatorName) || !Creator.corp.toString().equals(creatorName)) {
                return false;
            } else {
                return true;
            }
    }
}
