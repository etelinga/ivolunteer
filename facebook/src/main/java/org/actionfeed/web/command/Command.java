package org.actionfeed.web.command;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Used to add model items to the current ModelAndView
 */
public interface Command {
    /**
     * Build any necessary data and attach it to the <code>ModelAndView</code> for use in display.
     * @param request The source request that might have values required to build the model objects.
     * @param mv The model and view as required by Spring.
     * @param args Any additional arguments required to build the model objects.
     */
    public void buildModels(HttpServletRequest request, ModelAndView mv, Object... args);
}
