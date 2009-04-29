package org.actionfeed.web.command;

import org.actionfeed.Constants;
import static org.actionfeed.Constants.EVENT_LIST;
import static org.actionfeed.Constants.FACEBOOK_MODEL;
import org.actionfeed.domain.model.Event;
import org.actionfeed.web.model.BaseFacebookModel;
import org.actionfeed.web.spring.BasePagedListHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
public class MyEventsCommand extends BaseCommand {

    public MyEventsCommand() {
    }

    @Override public void buildModels(HttpServletRequest request, ModelAndView mv, Object... args) {
        BaseFacebookModel facebookModel = (BaseFacebookModel) request.getAttribute(FACEBOOK_MODEL);
        if (null != facebookModel) {
            List<Event> events = applicationService.listEvents(facebookModel.getFb_sig_user(), null, 0);
            LOG.trace("Got stuff list with [{}]", events.size());
            BasePagedListHolder listHolder = getPagedListHolder(request, events, Constants.EVENT_LIST);
            mv.addObject(EVENT_LIST, listHolder);
        }
    }

}
