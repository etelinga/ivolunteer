package org.actionfeed.web.command;

import static org.actionfeed.Constants.FACEBOOK_MODEL;
import static org.actionfeed.Constants.EVENT_LIST;
import org.actionfeed.domain.model.Event;
import org.actionfeed.web.model.BaseFacebookModel;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Determines the users current list of events that are available in their area.
 */
public class HomeCommand extends BaseCommand {

    /**
     * Constructor HomeCommand creates a new HomeCommand instance.
     */
    public HomeCommand() {
    }

    /**
     * Method buildModels gets all of the necessary data for the user.
     *
     * @param request of type HttpServletRequest
     * @param mv of type ModelAndView
     * @param args of type Object...
     */
    @Override public void buildModels(HttpServletRequest request, ModelAndView mv, Object... args) {
        BaseFacebookModel facebookModel = (BaseFacebookModel) request.getAttribute(FACEBOOK_MODEL);
        if (null != facebookModel) {
            List<Event> events = applicationService.listEvents(facebookModel.getFb_sig_user(), null, 20);
            LOG.trace("Got [{}] events for volunteer [{}]", (events == null)?"empty":events.size(),
                facebookModel.getFb_sig_user());
            mv.addObject(EVENT_LIST, events);
        }
    }
}
