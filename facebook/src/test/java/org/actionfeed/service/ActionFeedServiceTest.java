package org.actionfeed.service;
/**
 *
 */

import org.actionfeed.SpringTestCase;
import org.actionfeed.domain.model.*;
import org.actionfeed.web.model.BaseFacebookModel;
import org.actionfeed.web.model.BaseFormModel;

import java.util.*;

public class ActionFeedServiceTest extends SpringTestCase {
// ------------------------------ FIELDS ------------------------------

    ApplicationService applicationService;

    public ActionFeedServiceTest() {
    }

// -------------------------- OTHER METHODS --------------------------

    public void testQuery() throws Exception {
//        actionFeedService.getSummaryQueryAnnotation();
    }

    public void testGetKeyField() throws Exception {
        Volunteer member = applicationService.getVolunteer("1");
        assertNotNull(member);
        String keyField = member.getKeyField();
        assertNotNull(keyField);
        Volunteer newMember = (Volunteer) applicationService.getModelUsingKeyField(keyField);
        assertNotNull(newMember);
        assertTrue(newMember.getFacebookId().equals(member.getFacebookId()));
    }

    public void testGetVolunteer() throws Exception {
        Volunteer member = applicationService.getVolunteer("1");
        assertNotNull("Missing member", member);
    }

    public void testGetVolunteerProfile() throws Exception {
        Volunteer member = applicationService.getVolunteerProfile("1");
        assertNotNull(member);
    }

    public void testSaveMemberProfile() throws Exception {
        Volunteer member = new Volunteer();
        member.setFacebookId("641244915");
        member.setLastLogin(new Date());
        member.setSessionKey("dek3m33j");
        member.setEmailAddress("a@b.c");
        applicationService.saveVolunteerProfile(member);
        Volunteer test = applicationService.getVolunteer("641244915");
        assertNotNull(test);
        test = (Volunteer) applicationService.getModel(Volunteer.class, 641244915L);
        assertNotNull(test);
    }

    public void testDeleteMember() throws Exception {
        applicationService.deleteVolunteer("3");
        Volunteer member = applicationService.getVolunteer("3");
        assertNull(member);
    }

    public void configureBaseFormModel(BaseFormModel model, String facebookId) {
        model.setFb_sig_user((facebookId == null)?"641244912":facebookId);
        model.setFb_sig_friends("552916988,622552735,633221973,653969472,719754054,93738832");
        model.setFb_sig_session_key("1c1ebaee81aa1f42048de32d-izxTBgLjpGQ0yVaKaIJ2mzQ..");
    }

    public void configureBaseFacebookModel(BaseFacebookModel model, String facebookId) {
        model.setFb_sig_user((facebookId == null)?"641244912":facebookId);
        model.setFb_sig_friends("552916988,622552735,633221973,653969472,719754054,93738832");
        model.setFb_sig_session_key("1c1ebaee81aa1f42048de32d-izxTBgLjpGQ0yVaKaIJ2mzQ..");
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        applicationService = (ApplicationService) ctx.getBean("service");
    }
}