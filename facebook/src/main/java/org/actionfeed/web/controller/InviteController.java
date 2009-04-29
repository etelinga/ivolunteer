package org.actionfeed.web.controller;

import org.actionfeed.web.model.InviteFormModel;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the submission of friends to invite to use the application.
 */
public class InviteController extends BaseSimpleFormController {

    public InviteController() {
    }

    /**
     * @param request current HTTP request
     * @return the backing object
     * @throws Exception in case of invalid state or arguments
     */
    @Override protected Object formBackingObject(HttpServletRequest request) throws Exception {
        InviteFormModel formModel = (InviteFormModel) getCommandClass().newInstance();
        LOGGER.trace("Build invite model [{}]",formModel.toString());
        return formModel;
    }


    /**
     * Since FB sends in the braces from the POST and the ServletRequestDataBinder will barf on the [] since
     * it thinks it should have an index, we disallow the ids field and handle the member id's directly when
     * the onSubmit is called by pulling the values from the request.
     *
     * @param binder  The data binder.
     * @param request The source request.
     */
    @Override protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.setDisallowedFields(new String[]{"ids[]"});
    }

    /**
     * We have to get the id's from the friend selector and create a message that we'll post on the jms queue if
     * we can so that notifications are sent out that we've added a new item to our stuff.
     *
     * @param request  The servlet request
     * @param response Resp
     * @param command  The source model.
     * @param errors   No binding errors can occur.
     * @return Where we're going.
     * @throws Exception Not usual.
     */
    @Override protected ModelAndView onSubmit(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command, BindException errors) throws Exception {

        LOGGER.trace("Processing invite request");
        ModelAndView modelAndView = new ModelAndView(getRedirectView());
        if (null != command) {
            InviteFormModel model = (InviteFormModel) command;
            String[] ids = ServletRequestUtils.getStringParameters(request, "ids[]");
            if (null != ids) {
                LOGGER.trace("Got [{}] ids for inviting", ids.length);
                model.setIds(ids);
            }
        }

        if (null != viewCommand) {
            viewCommand.buildModels(request, modelAndView);
        }

        return modelAndView;
    }

}
