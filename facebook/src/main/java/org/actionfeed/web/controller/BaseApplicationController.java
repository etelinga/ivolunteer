package org.actionfeed.web.controller;

import org.actionfeed.service.FacebookService;
import org.actionfeed.service.ApplicationService;
import org.actionfeed.web.command.Command;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  When an subclass is wired a command should be associate with the view. The command is
 * used to build up all of the models to place in the MVC.
 */
public class BaseApplicationController extends AbstractController {
// ------------------------------ FIELDS ------------------------------

    protected final Logger log = LoggerFactory.getLogger(BaseApplicationController.class);
    protected ApplicationService applicationService;
    protected FacebookService facebookService;
    protected Command viewCommand;
    protected String viewName;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructor BaseApplicationController creates a new BaseApplicationController instance.
     */
    protected BaseApplicationController() {
    }

    /**
     * Template method. Subclasses must implement this.
     * The contract is the same as for <code>handleRequest</code>.
     *
     * @see #handleRequest
     */
    @Override protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        if(null != viewCommand) {
            log.trace("Building up view data for [{}]", viewCommand.getClass().getSimpleName());
            ModelAndView modelAndView = new ModelAndView(viewName);
            viewCommand.buildModels(request, modelAndView);
            return modelAndView;
        }

        return null;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Method getViewCommand returns the viewCommand of this BaseApplicationController object.
     *
     * @return the viewCommand (type Command) of this BaseApplicationController object.
     */
    public Command getViewCommand() {
        return viewCommand;
    }

    /**
     * Method setViewCommand sets the viewCommand of this BaseApplicationController object.
     *
     * @param viewCommand the viewCommand of this BaseApplicationController object.
     *
     */
    public void setViewCommand(Command viewCommand) {
        this.viewCommand = viewCommand;
    }

    /**
     * Method getFacebookService returns the facebookService of this BaseApplicationController object.
     *
     * @return the facebookService (type FacebookService) of this BaseApplicationController object.
     */
    public FacebookService getFacebookService() {
        return facebookService;
    }

    /**
     * Method setFacebookService sets the facebookService of this BaseApplicationController object.
     *
     * @param facebookService the facebookService of this BaseApplicationController object.
     *
     */
    public void setFacebookService(FacebookService facebookService) {
        this.facebookService = facebookService;
    }

    /**
     * Method getApplicationService returns the applicationService of this BaseApplicationController object.
     *
     * @return the applicationService (type ApplicationService) of this BaseApplicationController object.
     */
    public ApplicationService getApplicationService() {
        return applicationService;
    }

    /**
     * Method setApplicationService sets the applicationService of this BaseApplicationController object.
     *
     * @param applicationService the applicationService of this BaseApplicationController object.
     *
     */
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Method getViewName returns the viewName of this BaseApplicationController object.
     *
     * @return the viewName (type String) of this BaseApplicationController object.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Method setViewName sets the viewName of this BaseApplicationController object.
     *
     * @param viewName the viewName of this BaseApplicationController object.
     *
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
