package org.actionfeed.web.command;

import static org.actionfeed.Constants.FACEBOOK_MODEL;
import org.actionfeed.SpringServiceTestCase;
import org.actionfeed.web.model.BaseFacebookModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
public class HomeCommandTest extends SpringServiceTestCase {

    public HomeCommandTest() {
    }

    public void testBuildModels() throws Exception {
        HomeCommand homeCommand = new HomeCommand();
        homeCommand.setActionFeedService(applicationService);
        ModelAndView mv = new ModelAndView();
        MockHttpServletRequest request = buildMockRequest();
        homeCommand.buildModels(request, mv);
        assertNotNull(mv);
    }

    public MockHttpServletRequest buildMockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        BaseFacebookModel facebookModel = new BaseFacebookModel();
        facebookModel.setFb_sig_user("641244912");
        request.setAttribute(FACEBOOK_MODEL, facebookModel);
        return request;
    }
}
