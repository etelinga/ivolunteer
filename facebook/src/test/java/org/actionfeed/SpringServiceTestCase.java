package org.actionfeed;

import org.actionfeed.service.FacebookService;
import org.actionfeed.service.ApplicationService;

/**
 *
 */
public class SpringServiceTestCase extends SpringTestCase {

    protected ApplicationService applicationService;

    protected FacebookService facebookService;

    public SpringServiceTestCase() {
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        applicationService = (ApplicationService) ctx.getBean("service");
        facebookService = (FacebookService) ctx.getBean("facebookService");
    }
}
