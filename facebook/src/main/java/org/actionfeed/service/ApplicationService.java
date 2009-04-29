package org.actionfeed.service;

import org.actionfeed.domain.model.Event;
import org.actionfeed.domain.model.Volunteer;
import org.actionfeed.web.model.BaseFacebookModel;
import org.actionfeed.web.model.EventFormModel;
import org.hibernate.Query;
import static org.springframework.util.StringUtils.hasText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class defines all business logic including communication with the database.
 */
public class ApplicationService extends BaseService {

    /**
     * Speed up the split by pre-compiling the pattern
     */
    private static final Pattern PATTERN = Pattern.compile(",");

    /**
     * Constructor ApplicationService creates a new ApplicationService instance.
     */
    public ApplicationService() {
    }

    /**
     * Saves a member model based on updated information from the FB request.
     *
     * @param model The source request model. Everything is string based on the form model.
     */
    public void saveVolunteer( final BaseFacebookModel model) {
        cacheService.resetVolunteerCache(model.getFb_sig_user());
        Volunteer volunteer = getVolunteer(model);
        if (null != volunteer) {
            logger.trace("Found volunteer [{}]", volunteer.getFacebookId());
            if (null == volunteer.getLastLogin()) {
                volunteer.setLastLogin(new Date());
            }
            if (model.isApplicationAdded()) {
                volunteer.setApplicationAdded(true);
            }
            if (model.isInfiniteSession()) {
                volunteer.setSessionKey(model.getFb_sig_session_key());
                volunteer.setInfiniteSession(true);
            } else {
                String sessionKey = volunteer.getSessionKey();
                String sigKey = model.getFb_sig_session_key();
                if (null != sessionKey && null != sigKey) {
                    if (!sessionKey.equals(sigKey)) {
                        volunteer.setSessionKey(sigKey);
                        volunteer.setLastLogin(new Date());
                        volunteer.setInfiniteSession(false);
                        logger.trace("Set new session key [{}]", sigKey);
                    } else {
                        logger.trace("No update required.");
                        return;
                    }
                }
            }
        } else {
            volunteer = new Volunteer();
            volunteer.setSessionKey(model.getFb_sig_session_key());
            volunteer.setFacebookId(model.getFb_sig_user());
            volunteer.setLastLogin(new Date());
            if(model.isInfiniteSession()) {
                volunteer.setInfiniteSession(true);
            }
            if(model.isApplicationAdded()) {
                volunteer.setApplicationAdded(true);
            }
            logger.trace("Created a new volunteer [{}]", volunteer.getFacebookId());
        }

        volunteer.setProfileUpdateDate(model.getProfileUpdateDate());
        volunteer.setSessionExpiryDate(model.getSessionExpiryDate());

        dao.saveModel(volunteer);
        logger.trace("Saved volunteer [{}]", volunteer.toString());
    }

    /**
     * Get the full profile for the given facebook id.
     *
     * @param facebookId The id to locate the user with.
     * @return The member or null.
     */
    public Volunteer getVolunteerProfile( final String facebookId) {
        logger.trace("Getting the profile for [{}]", facebookId);
        if (hasText(facebookId)) {
            try {
                Long id = Long.parseLong(facebookId);
                Query hibernateQuery = dao.createQueryByName("findVolunteer");
                hibernateQuery.setParameter("facebookId", id);
                return (Volunteer) hibernateQuery.uniqueResult();
            } catch (NumberFormatException ne) {
                // Ignore NFE only.
            }
        }
        return null;
    }

    /**
     * Get the full profile for the given member.
     * @param model The base facebook model
     * @return The member if found otherwise null.
     */
    public Volunteer getVolunteerProfile(final BaseFacebookModel model) {
        return getVolunteerProfile(model.getFb_sig_user());
    }

    /**
     * Save updated profile information.
     * @param member The member with updated information.
     * @return The update model.
     */
    public Volunteer saveVolunteerProfile( final Volunteer member) {
        logger.trace("Saving member profile [{}]", member.getId());
        dao.saveModel(member);
        return member;
    }

    /**
     * Get the database member for the given facebook id.
     *
     * @param facebookId The member id to obtain.
     * @return The member or null if the member doesn't exist.
     */
    public Volunteer getVolunteer( final String facebookId) {
        if (hasText(facebookId)) {
            try {
                Long id = Long.parseLong(facebookId);
                Query hibernateQuery = dao.createQueryByName("findVolunteer");
                hibernateQuery.setParameter("facebookId", id);
                return (Volunteer) hibernateQuery.uniqueResult();
            } catch (NumberFormatException nfe) {
                // Only ignore number format exceptions. 
            }
        }
        return null;
    }

    /**
     * @param model The source for locating a member. Uses the facebookID to locate the member.
     * @return The member or null if not found.
     */
    public Volunteer getVolunteer( final BaseFacebookModel model) {
        return getVolunteer(model.getFb_sig_user());
    }

    /**
     * Remove a member and all associated rows
     *
     * @param facebookId The id of the member
     */
    public void deleteVolunteer( final String facebookId) {
        logger.trace("Deleting member [{}]", facebookId);
        cacheService.resetVolunteerCache(facebookId);
        if (hasText(facebookId)) {
            Volunteer member = getVolunteer(facebookId);
            if (null != member) {
                dao.deleteModel(Volunteer.class, member.getId());
                logger.trace("Deleted member [{}]", facebookId);
            }
        }
    }



    /**
     * Method getFriendsFromModel ...
     *
     * @param model of type BaseFacebookModel
     * @return List<String>
     */
    private List<String> getFriendsFromModel(final BaseFacebookModel model) {
        String friends = model.getFb_sig_friends();
        if (hasText(friends)) {
            String[] ids = PATTERN.split(friends);
            return Arrays.asList(ids);
        }

        return new ArrayList<String>();
    }

    /**
     * Method listEvents ...
     *
     * @param fb_sig_user of type String
     * @param start of type Integer
     * @param count of type Integer
     * @return List<Event>
     */
    public List<Event> listEvents(String fb_sig_user, final Integer start, final Integer count) {
        return null;
    }

    /**
     * Method saveEvent ...
     *
     * @param model of type EventFormModel
     */
    public void saveEvent(EventFormModel model) {

    }
}
