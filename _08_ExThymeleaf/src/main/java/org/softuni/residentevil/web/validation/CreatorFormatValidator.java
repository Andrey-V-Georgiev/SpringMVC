package org.softuni.residentevil.web.validation;

import org.softuni.residentevil.domain.entities.Creator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreatorFormatValidator implements ConstraintValidator<CreatorFormat, Creator> {

    @Override
    public boolean isValid(Creator creatorName, ConstraintValidatorContext context) {

        boolean validCreator = Creator.Corp.equals(creatorName) || Creator.corp.equals(creatorName);
        return validCreator;
    }
}
