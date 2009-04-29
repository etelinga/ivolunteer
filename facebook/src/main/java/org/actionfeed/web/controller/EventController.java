package org.actionfeed.web.controller;

import org.actionfeed.Constants;
import static org.actionfeed.Constants.FACEBOOK_MODEL;
import org.actionfeed.domain.model.Event;
import org.actionfeed.web.model.BaseFacebookModel;
import org.actionfeed.web.spring.BasePagedListHolder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 */
public class EventController extends BaseApplicationController {

    private static final int PAGE_SIZE = 5;
    private static final int MAX_PAGES = 3;

    /**
     * Constructor EventController creates a new instance.
     */
    public EventController() {
    }

    /**
     * Template method. Subclasses must implement this.
     * The contract is the same as for <code>handleRequest</code>.
     *
     * @see #handleRequest
     */
    @Override protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView(viewName);

        BaseFacebookModel facebookModel = (BaseFacebookModel) request.getAttribute(FACEBOOK_MODEL);
        if(null != facebookModel) {
            List<Event> events = applicationService.listEvents(facebookModel.getFb_sig_user(), null, null);
            BasePagedListHolder holder = new BasePagedListHolder();
            holder.setSource(events);
            holder.setPageSize(PAGE_SIZE);
            holder.setMaxLinkedPages(MAX_PAGES);
            holder.setPage(0);
            // A binder will take request parameters and set the properties on the beans using Spring converters.
            ServletRequestDataBinder binder = new ServletRequestDataBinder(holder, Constants.EVENT_LIST);
            binder.bind(request);
            // Make the data available to the JSP page.
            modelAndView.addObject(Constants.EVENT_LIST, holder);
        }

        return modelAndView;
    }
}
