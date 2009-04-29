package org.actionfeed.web.controller;

import static org.actionfeed.Constants.FACEBOOK_MODEL;
import org.actionfeed.domain.model.Volunteer;
import org.actionfeed.web.model.BaseFacebookModel;
import org.actionfeed.web.model.ProfileFormModel;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class EditProfileController extends FacebookSimpleFormController {

    /**
     * Constructs a new AfSimpleFormController.
     */
    public EditProfileController() {
    }

    /**
     * @param request current HTTP request
     * @return the backing object
     * @throws Exception in case of invalid state or arguments
     */
    @Override protected Object formBackingObject(HttpServletRequest request) throws Exception {
        BaseFacebookModel facebookModel = (BaseFacebookModel) request.getAttribute(FACEBOOK_MODEL);

        ProfileFormModel formModel = new ProfileFormModel();

        formModel.setReferrerURI(request.getRequestURI());
        
        if (null != facebookModel) {
            LOGGER.trace("Create form backing for member profile [{}]", facebookModel.getFb_sig_user());
            // Get all of the facebook parameters for the incoming request.
            BeanUtils.copyProperties(facebookModel, formModel);
            // See if we are editing or creating a new object.
            Volunteer member = applicationService.getVolunteerProfile(facebookModel);
            if (null == member) {
                    LOGGER.debug("Unable to locate the member [{}]", formModel.getFb_sig_user());
            } else {
                LOGGER.trace("Built profile for [{}]", member.getId());
            }
        }

        return formModel;
    }


    /**
     * Method onSubmit is called during the submit phase of the form.
     *
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param command of type Object
     * @param errors of type BindException
     * @return ModelAndView
     * @throws Exception when
     */
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                                    Object command, BindException errors) throws Exception {

        ModelAndView modelAndView = new ModelAndView(getRedirectView());

        BaseFacebookModel facebookModel = (BaseFacebookModel) request.getAttribute(FACEBOOK_MODEL);

        LOGGER.trace("Processing profile change request.");

        // See if we are editing or creating a new object.
        Volunteer member = applicationService.getVolunteerProfile(facebookModel);
        if (null != member) {
            LOGGER.trace("Saving updated profile [{}]", facebookModel.getFb_sig_user());
            ProfileFormModel model = (ProfileFormModel) command;
            member.setNotifyFriends(model.getNotifyFriends());
            applicationService.saveVolunteerProfile(member);
        } else {
            LOGGER.trace("Couldn't update profile [{}]", facebookModel.getFb_sig_user());
        }

        if (null != viewCommand) {
                viewCommand.buildModels(request, modelAndView);
            }

        return modelAndView;
    }
}
