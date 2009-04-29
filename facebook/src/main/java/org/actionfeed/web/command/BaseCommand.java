package org.actionfeed.web.command;

import static org.actionfeed.Constants.MAX_PAGES;
import static org.actionfeed.Constants.PAGE_SIZE;
import org.actionfeed.domain.model.Model;
import org.actionfeed.service.ApplicationService;
import org.actionfeed.service.FacebookService;
import org.actionfeed.web.spring.BasePagedListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Follows the command pattern required by Spring MVC.
 */
public abstract class BaseCommand implements Command {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseCommand.class);
    protected ApplicationService applicationService;
    protected FacebookService facebookService;

    /**
     * Constructor BaseCommand creates a new BaseCommand instance without any initialization.
     */
    protected BaseCommand() {
    }

    /**
     * For building within a service. May also be wired via spring.
     *
     * @param applicationService      Give access to the service framework
     * @param facebookService Give access to the Facebook API.
     */
    protected BaseCommand(ApplicationService applicationService, FacebookService facebookService) {
        this.applicationService = applicationService;
        this.facebookService = facebookService;
    }

    @Override public abstract void buildModels(HttpServletRequest request, ModelAndView mv, Object... args);

    /**
     * Builder a paged list holder for a result obtained from the database.
     * @param request The source servlet request.
     * @param result The database result list.
     * @param requestName The name of the source result constant.
     * @return A based list holder
     */

    protected BasePagedListHolder getPagedListHolder( final HttpServletRequest request,
                                                      final List<? extends Model> result,
                                                      final String requestName) {
        BasePagedListHolder holder = new BasePagedListHolder();
        holder.setSource(result);
        holder.setPageSize(PAGE_SIZE);
        holder.setMaxLinkedPages(MAX_PAGES);
        holder.setPage(0);
        // A binder will take request parameters and set the properties on the beans using Spring converters.
        ServletRequestDataBinder binder = new ServletRequestDataBinder(holder, requestName);
        binder.bind(request);
        return holder;
    }

    /**
     * Method setPmsService sets the pmsService of this BaseCommand object.
     * @param applicationService the pmsService of this BaseCommand object.
     */
    public void setActionFeedService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Method setFacebookService sets the facebookService of this BaseCommand object.
     * @param facebookService the facebookService of this BaseCommand object.
     */
    public void setFacebookService(FacebookService facebookService) {
        this.facebookService = facebookService;
    }
}
