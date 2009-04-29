package org.actionfeed.web.controller;

import org.actionfeed.Constants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class HomeController extends BaseApplicationController {
// ------------------------------ FIELDS ------------------------------

    private String aboutViewName;

    private String homeViewName;

    /**
     * Constructor creates a new home controller without any internal initializations.
     */
    public HomeController() {
    }

    /**
     * Template method. Subclasses must implement this.
     * The contract is the same as for <code>handleRequest</code>.
     *
     * @see #handleRequest
     */
    @Override protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                           HttpServletResponse response) throws Exception {
        // See if we have a session key. If we do then we display the home page otherwise, the about.
        log.trace("Handling the volunteer request");
        String sessionKey = (String) WebUtils.getSessionAttribute(request, Constants.SESSION_KEY);
        ModelAndView modelAndView;
        if (StringUtils.hasText(sessionKey)) {
            log.trace("Sending the volunteer to the home view");
            modelAndView = new ModelAndView(homeViewName);
            viewCommand.buildModels(request, modelAndView);
        } else {
            log.trace("Sending the volunteer to the about view");
            modelAndView = new ModelAndView(aboutViewName);
        }

        return modelAndView;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAboutViewName() {
        return aboutViewName;
    }

    public void setAboutViewName(String aboutViewName) {
        this.aboutViewName = aboutViewName;
    }

    public String getHomeViewName() {
        return homeViewName;
    }

    public void setHomeViewName(String homeViewName) {
        this.homeViewName = homeViewName;
    }
}
