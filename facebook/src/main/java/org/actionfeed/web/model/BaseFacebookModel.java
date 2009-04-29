package org.actionfeed.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * This is ugly but matches the incoming post from FB. It makes mapping
 * easier with the validator to match the incoming request parameters.
 * Extends this class with the necessary form parameters used within the
 * interface.
 */
public class BaseFacebookModel implements Serializable, FacebookModel {
// ------------------------------ FIELDS ------------------------------

    private String auth_token;
    private String fb_in_canvas;
    private String fb_sig;
    private String fb_sig_added;
    private String fb_sig_api_key;
    private String fb_sig_expires;
    private String fb_sig_friends;
    private String fb_sig_profile_update_time;
    private String fb_sig_session_key;
    private String fb_sig_time;
    private String fb_sig_user;

// --------------------------- CONSTRUCTORS ---------------------------

    public BaseFacebookModel() {
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getFb_in_canvas() {
        return fb_in_canvas;
    }

    public void setFb_in_canvas(String fb_in_canvas) {
        this.fb_in_canvas = fb_in_canvas;
    }

    public String getFb_sig() {
        return fb_sig;
    }

    public void setFb_sig(String fb_sig) {
        this.fb_sig = fb_sig;
    }

    public String getFb_sig_added() {
        return fb_sig_added;
    }

    public void setFb_sig_added(String fb_sig_added) {
        this.fb_sig_added = fb_sig_added;
    }

    public String getFb_sig_api_key() {
        return fb_sig_api_key;
    }

    public void setFb_sig_api_key(String fb_sig_api_key) {
        this.fb_sig_api_key = fb_sig_api_key;
    }

    public String getFb_sig_expires() {
        return fb_sig_expires;
    }

    public void setFb_sig_expires(String fb_sig_expires) {
        this.fb_sig_expires = fb_sig_expires;
    }

    public String getFb_sig_friends() {
        return fb_sig_friends;
    }

    public void setFb_sig_friends(String fb_sig_friends) {
        this.fb_sig_friends = fb_sig_friends;
    }

    public String getFb_sig_profile_update_time() {
        return fb_sig_profile_update_time;
    }

    public void setFb_sig_profile_update_time(String fb_sig_profile_update_time) {
        this.fb_sig_profile_update_time = fb_sig_profile_update_time;
    }

    public String getFb_sig_session_key() {
        return fb_sig_session_key;
    }

    public void setFb_sig_session_key(String fb_sig_session_key) {
        this.fb_sig_session_key = fb_sig_session_key;
    }

    public String getFb_sig_time() {
        return fb_sig_time;
    }

    public void setFb_sig_time(String fb_sig_time) {
        this.fb_sig_time = fb_sig_time;
    }

    public String getFb_sig_user() {
        return fb_sig_user;
    }

    public void setFb_sig_user(String fb_sig_user) {
        this.fb_sig_user = fb_sig_user;
    }

    /**
     * @return If we have an infinite FB session result is true.
     */
    public boolean isInfiniteSession() {
        return null != fb_sig_expires && fb_sig_expires.equals("0");
    }

    /**
     * @return True if the user as added the application.
     */
    public boolean isApplicationAdded() {
        return null != fb_sig_added && !fb_sig_added.equals("0");
    }

    /**
     * FB places the an expiry date in seconds or 0 if the session never expires.
     * This method will return null if the session doesn't expire or isn't defined.
     * @return The expiry date or null if the session is infinite or not defined.
     */
    public Date getSessionExpiryDate() {
        if(!isInfiniteSession()) {
            return getAdjustedDate(fb_sig_expires);
        }
        return null;
    }

    /**
     * @return The profile update time based on the information in the request.
     */
    public Date getProfileUpdateDate() {
        return getAdjustedDate(fb_sig_profile_update_time);
    }

    /**
     * @return The date of the last request.
     */
    public Date getFacebookDate() {
        return getAdjustedDate(fb_sig_time);
    }

    /**
     * Convert a FB based date stamp based on the current server time sent by FB and
     * convert seconds to millis and create a date.
     * @param sourceDate The source string date.
     * @return The converted date or null if the source date is null or has no data.
     */
    private Date getAdjustedDate(String sourceDate) {
        long adjustment = 0;
        if(null != sourceDate && sourceDate.trim().length() > 0) {
            try {
                long l = Long.parseLong(sourceDate);
                return new Date((l + adjustment) * 1000);
            } catch (NumberFormatException nf) {}
        }

        return null;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BaseFacebookModel");
        sb.append("{fb_sig_profile_update_time='").append(fb_sig_profile_update_time).append('\'');
        sb.append(", fb_sig_session_key='").append(fb_sig_session_key).append('\'');
        sb.append(", fb_sig_added='").append(fb_sig_added).append('\'');
        sb.append(", fb_sig_time='").append(fb_sig_time).append('\'');
        sb.append(", fb_sig_api_key='").append(fb_sig_api_key).append('\'');
        sb.append(", fb_sig_friends='").append(fb_sig_friends).append('\'');
        sb.append(", fb_sig_user='").append(fb_sig_user).append('\'');
        sb.append(", fb_in_canvas='").append(fb_in_canvas).append('\'');
        sb.append(", fb_sig_expires='").append(fb_sig_expires).append('\'');
        sb.append(", fb_sig='").append(fb_sig).append('\'');
        sb.append(", auth_token='").append(auth_token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
