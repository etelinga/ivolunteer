package org.actionfeed.web.controller;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.validation.BindException;
import org.springframework.util.StringUtils;
import org.actionfeed.service.ApplicationService;
import org.actionfeed.service.FacebookService;
import org.actionfeed.web.command.Command;
import org.actionfeed.Constants;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class BaseSimpleFormController extends SimpleFormController {
// ------------------------------ FIELDS ------------------------------

    /**
     * Defines the interaction service with database
     */
    protected ApplicationService applicationService;
    /**
     * Defines the interaction service with the FB api
     */
    protected FacebookService facebookService;
    /**
     * Defines the viewCommand that is issued upon success to place the appropriate models in the request.
     */
    protected Command viewCommand;

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseSimpleFormController.class);

// --------------------------- CONSTRUCTORS ---------------------------

    /** Constructs a new BaseSimpleFormController. */
    public BaseSimpleFormController() {
    }

    /**
     * @return A redirect view that will force the caller to redirect the brower to the new location for
     *         the success view. This should prevent duplicate requests but still need to check for duplicate tab
     *         submits.
     */
    protected RedirectView getRedirectView() {
        // todo: determine how to get the default extension from web.xml
        String redirect = getSuccessView() + ".xhtml";
        RedirectView rv = new RedirectView(redirect);
        rv.setContextRelative(true);
        rv.setExposeModelAttributes(false);
        LOGGER.trace("Redirecting to [{}]", rv.getUrl());
        return rv;
    }

    /**
     * Facebook data always comes in via POST so we need to differentiate a new request using a different
     * methodology. Since every form we post should have the hidden field <pre>keyField<pre> information so we can
     * validate the data, it will be used to determine if we have a form submission. If it's null that means
     * it's the first time through. Once we have a model the field will be there everytime.
     *
     * @param request current HTTP request
     * @return if the request represents a form submission
     */
    @Override protected boolean isFormSubmission(HttpServletRequest request) {
        try {
            String s = ServletRequestUtils.getStringParameter(request, Constants.FORM_EDIT);
            // Leave this in here since I seem to forget to put this hidden field in so often.
            LOGGER.trace("New form presented, no formId found. Make sure to add in the hidden field in the form.");
            return (null != s);
        } catch (ServletRequestBindingException e) {
            return false;
        }
    }

    /**
     * Determine if the user is deleting the requested model element. If so don't perform any validaton.
     * @param request current HTTP request
     * @param command the command object to validate
     * @param errors  validation errors holder, allowing for additional
     *                custom registration of binding errors
     * @return whether to suppress validation for the given request
     */
    @Override protected boolean suppressValidation(HttpServletRequest request, Object command, BindException errors) {
        try {
            String deleteAction = ServletRequestUtils.getStringParameter(request, "delete");
            if(StringUtils.hasText(deleteAction))
                return true;
        } catch (ServletRequestBindingException e) {
            LOGGER.warn("Unable to bind and locate the delete action");
        }

        return super.suppressValidation(request, command, errors);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * @return Value for property 'facebookService'.
     */
    public FacebookService getFacebookService() {
        return facebookService;
    }

    /**
     * @param facebookService Value to set for property 'facebookService'.
     */
    public void setFacebookService(FacebookService facebookService) {
        this.facebookService = facebookService;
    }

    /**
     * @return Value for property 'pmsService'.
     */
    public ApplicationService getApplicationService() {
        return applicationService;
    }

    /**
     * @param applicationService Value to set for property 'pmsService'.
     */
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * @return Value for property 'viewCommand'.
     */
    public Command getViewCommand() {
        return viewCommand;
    }

    /**
     * @param viewCommand Value to set for property 'viewCommand'.
     */
    public void setViewCommand(Command viewCommand) {
        this.viewCommand = viewCommand;
    }
}
