package org.actionfeed.web.spring;

import org.actionfeed.service.ApplicationService;
import org.actionfeed.util.ActionFeedException;
import org.actionfeed.web.command.Command;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 */
public class ApplicationExceptionResolver extends SimpleMappingExceptionResolver {
// ------------------------------ FIELDS ------------------------------

    protected ApplicationService service;

    protected Command defaultCommand;

    protected String failureView;

    protected Map viewCommands;

// --------------------------- CONSTRUCTORS ---------------------------

    /** Constructs a new PmsExceptionResolver. */
    public ApplicationExceptionResolver() {
    }

    /** {@inheritDoc} */
    @Override
    protected ModelAndView getModelAndView(String viewName, Exception ex, HttpServletRequest request) {

        if(null == failureView) {
            if (logger.isDebugEnabled()) logger.debug("Need to define a failure view! No redirection performed");
            return null;
        }

        ModelAndView mv = null;
        if (ex instanceof ActionFeedException) {
            mv = new ModelAndView(viewName);
            Command command = defaultCommand;
            if (null != viewCommands && viewCommands.containsKey(viewName)) {
                command = (Command) viewCommands.get(viewName);
            }
            command.buildModels(request, mv);
        } else {
            mv = new ModelAndView(failureView);
        }
        
        mv.addObject(DEFAULT_EXCEPTION_ATTRIBUTE, ex);
        return mv;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * @return Value for property 'defaultCommand'.
     */
    public Command getDefaultCommand() {
        return defaultCommand;
    }

    /**
     * @param defaultCommand Value to set for property 'defaultCommand'.
     */
    public void setDefaultCommand(Command defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    /**
     * @return Value for property 'service'.
     */
    public ApplicationService getService() {
        return service;
    }

    /**
     * @param service Value to set for property 'service'.
     */
    public void setService(ApplicationService service) {
        this.service = service;
    }

    /**
     * @return Value for property 'viewCommands'.
     */
    public Map getViewCommands() {
        return viewCommands;
    }

    /**
     * @param viewCommands Value to set for property 'viewCommands'.
     */
    public void setViewCommands(Map viewCommands) {
        this.viewCommands = viewCommands;
    }

    public String getFailureView() {
        return failureView;
    }

    public void setFailureView(String failureView) {
        this.failureView = failureView;
    }
}
