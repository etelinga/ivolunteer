package org.actionfeed.web.validator;

import org.actionfeed.web.model.BaseFormModel;
import org.actionfeed.web.model.ProfileFormModel;
import org.hibernate.validator.ClassValidator;
import org.springframework.validation.Errors;

/**
 * This class validates any data entry fields in the profile form and uses the hibernate
 * validator by default.
 */
public class ProfileValidator extends BaseValidator {
    private static final ClassValidator<ProfileFormModel> HIBERNATE_MODEL_VALIDATOR =
        new ClassValidator<ProfileFormModel>(ProfileFormModel.class);

    public ProfileValidator() {
    }

    /**
     * @{inheritDoc}
     */
    @Override public boolean supports(Class clazz) {
        return ProfileFormModel.class.isAssignableFrom(clazz);
    }

    /**
     * @{inheritDoc}
     */
    @Override public void validate(Object target, Errors errors) {
        validateUsingHibernateAnnotations(target, errors, HIBERNATE_MODEL_VALIDATOR);
    }

}
