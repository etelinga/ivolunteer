package org.actionfeed.web.validator;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Provides common functionality for any form that needs validation of any entries and would
 * like access to using Hibernate validation annotations.
 */
public abstract class BaseValidator implements Validator {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseValidator.class);

    protected BaseValidator() {
    }

    /**
     * A class must return true if it supports validation.
     * @param clazz The name of the class.
     * @return True if validation support is provided.
     */
    @Override public abstract boolean supports(Class clazz);

    /**
     * The actual validation that occurs once the user has submitted the form.
     * @param target The source model.
     * @param errors A container for any errors found.
     */
    @Override public abstract void validate(Object target, Errors errors);

    /**
     * todo: Fix the generics
     * @param target The object from the POST
     * @param errors The errors container to push back to the view.
     * @param validator A Hibernate ClassValidator to use in checking the model object.
     */
    @SuppressWarnings("unchecked")
    protected void validateUsingHibernateAnnotations(Object target,
                                                     Errors errors,
                                                     ClassValidator validator) {
        InvalidValue[] invalidValues = validator.getInvalidValues(target);
        for (int i = 0; i < invalidValues.length; i++) {
            errors.rejectValue(invalidValues[i].getPropertyName(), "field.validator.error",
                new Object[] {invalidValues[i].getMessage()}, "Missing");
        }
    }

}
