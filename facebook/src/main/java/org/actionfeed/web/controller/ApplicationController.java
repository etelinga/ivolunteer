package org.actionfeed.web.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple test setup controller.
 */
public class ApplicationController extends MultiActionController {

    public ApplicationController() {
    }

    public ModelAndView defaultRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("home");
    }

    public ModelAndView newStuff(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("stuff");
    }

    public ModelAndView showStuff(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("stuff");
    }
}
