package org.actionfeed.facebook.api;

/**
 *
 */
public enum FacebookRequestParameters {

    AUTH_TOKEN("auth_token"),
    SIGNATURE_PROFILE_UPDATE_TIME("fb_sig_profile_update_time"),
    SIGNATURE_SESSION_KEY("fb_sig_session_key"),
    SIGNATURE_ADDED("fb_sig_added"),
    SIGNATURE_TIME("fb_sig_time"),
    SIGNATURE_API_KEY("fb_sig_api_key"),
    SIGNATURE_USER("fb_sig_user"),
    SIGNATURE_EXPIRES("fb_sig_expires"),
    SIGNATURE("fb_sig"),
    SIGNATURE_IN_IFRAME("fb_sig_in_iframe"),;

    private String parameterName;

    FacebookRequestParameters(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String toString() {
        return parameterName;
    }

    /**
     * Returns true if this field has a particular name.
     */
    public boolean isName(String name) {
        return toString().equals(name);
    }

}
