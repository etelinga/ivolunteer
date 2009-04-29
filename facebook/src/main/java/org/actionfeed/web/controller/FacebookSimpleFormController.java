package org.actionfeed.web.controller;

import static org.actionfeed.Constants.FACEBOOK_MODEL;
import static org.actionfeed.Constants.KEY_FIELD;
import org.actionfeed.domain.model.BaseModel;
import org.actionfeed.domain.model.Model;
import org.actionfeed.web.model.BaseFacebookModel;
import org.actionfeed.web.model.BaseFormModel;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Defines properties that are common to all form controllers used in the
 * FB application.
 */
public class FacebookSimpleFormController extends BaseSimpleFormController {

    /**
     * Constructs a new AfSimpleFormController.
     */
    public FacebookSimpleFormController() {
    }

    /**
     * @param request current HTTP request
     * @return the backing object
     * @throws Exception in case of invalid state or arguments
     */
    @Override protected Object formBackingObject(HttpServletRequest request) throws Exception {
        // We will always have a basic mode. Might want to throw a specific exception at this point for redirect.
        Model model = getModel(request);
        return getFormModel(request, model);
    }

    /**
     * Get the model for the key field presented in the request.
     *
     * @param request The servlet request.
     * @return A model from the database if it's available.
     * @throws Exception from the database layer.
     */
    protected Model getModel(HttpServletRequest request) throws Exception {
        BaseFacebookModel facebookModel = (BaseFacebookModel) request.getAttribute(FACEBOOK_MODEL);
        BaseModel model = null;
        if (null != facebookModel) {
            // Get all of the facebook parameters for the incoming request.
            String keyField = ServletRequestUtils.getStringParameter(request, KEY_FIELD);
            if (StringUtils.hasText(keyField)) {
                model = applicationService.getModelUsingKeyField(keyField);

                if (null == model) {
                    LOGGER.warn("Unable to locate the requested model [{}]:[{}]",
                        facebookModel.getFb_sig_user(), getCommandClass().getName());
                } else {
                    LOGGER.trace("Getting model for id [{}]", model.toString());
                }
            } else {
                LOGGER.trace("Create new model [{}]. No key field", getCommandClass().getSimpleName());
            }
        }
        return model;
    }


    /**
     * Method getFormModel ...
     *
     * @param request of type HttpServletRequest
     * @param model of type Model
     * @return The model form for this request.
     * @throws Exception when the data cannot be processed.  
     */
    protected BaseFormModel getFormModel(HttpServletRequest request, Model model) throws Exception {
        // We will always have a basic mode. Might want to throw a specific exception at this point for redirect.
        BaseFormModel formModel = (BaseFormModel) getCommandClass().newInstance();
        if (null != model) {
            BeanUtils.copyProperties(model, formModel);
        }
        BaseFacebookModel facebookModel = (BaseFacebookModel) request.getAttribute(FACEBOOK_MODEL);
        if (null != facebookModel) {
            // Get all of the facebook parameters for the incoming request.
            BeanUtils.copyProperties(facebookModel, formModel);
            String keyField = ServletRequestUtils.getStringParameter(request, KEY_FIELD);
            if (StringUtils.hasText(keyField)) {
                formModel.setKeyField(keyField);
            } else {
                LOGGER.trace("Create new model [{}]. No key field", getCommandClass().getSimpleName());
            }
        }
        formModel.setReferrerURI(request.getRequestURI());
        return formModel;
    }

}
