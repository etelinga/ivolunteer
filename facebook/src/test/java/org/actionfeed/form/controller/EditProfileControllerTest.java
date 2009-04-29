package org.actionfeed.form.controller;
/**
 *
 */

import org.actionfeed.SpringTestCase;
import org.actionfeed.service.ApplicationService;
import org.actionfeed.web.controller.EditProfileController;

public class EditProfileControllerTest extends SpringTestCase {
    private EditProfileController editProfileController = new EditProfileController();
    private ApplicationService applicationService;
    private final String EMAIL_BASE = "hello@hi.there.com";

    public EditProfileControllerTest() {
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        applicationService = (ApplicationService) ctx.getBean("service");
        ctx.getBean("profileController");
    }

}