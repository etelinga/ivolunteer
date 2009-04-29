package org.actionfeed.web.controller;

import static org.actionfeed.Constants.KEY_FIELD;
import org.actionfeed.util.KeyFieldEncoder;
import org.actionfeed.web.model.EventFormModel;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides CRUD functionality for the <code>Stuff</code> class.
 */
public class EditEventController extends FacebookSimpleFormController {

    /**
     * Constructs a new PmsSimpleFormController.
     */
    public EditEventController() {
    }

    /**
     * Called by super when the data is correct and ready to be persisted.
     *
     * @param request  current servlet request
     * @param response current servlet response
     * @param command  form object with request parameters bound onto it
     * @param errors   Errors instance without errors (subclass can add errors if it wants to)
     * @return the prepared model and view, or <code>null</code>
     * @throws Exception in case of errors
     */
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                                    Object command, BindException errors) throws Exception {

        ModelAndView modelAndView = new ModelAndView(getRedirectView());

        String deleteAction = ServletRequestUtils.getStringParameter(request, "delete");
        String keyField = ServletRequestUtils.getStringParameter(request, KEY_FIELD);
        if (null != keyField) {
            if (StringUtils.hasText(deleteAction)) {
                applicationService.deleteModel(keyField);
            } else {
                    LOGGER.trace("Saving updated event model [{}]", KeyFieldEncoder.getPrimaryKeyFromKeyField(keyField));
                EventFormModel model = (EventFormModel) command;
                model.setKeyField(keyField);
                applicationService.saveEvent(model);
            }
        }
        if (null != viewCommand) {
            viewCommand.buildModels(request, modelAndView);
        }

        return modelAndView;
    }

}
