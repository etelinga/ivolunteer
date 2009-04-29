package org.actionfeed.web.model;

/**
 *
 */
public interface FacebookModel {

    String getAuth_token();

    String getFb_in_canvas();

    String getFb_sig();

    String getFb_sig_added();

    String getFb_sig_api_key();

    String getFb_sig_expires();

    String getFb_sig_friends();

    String getFb_sig_profile_update_time();

    String getFb_sig_session_key();

    String getFb_sig_time();

    String getFb_sig_user();

    void setAuth_token(String auth_token);

    void setFb_in_canvas(String fb_in_canvas);

    void setFb_sig(String fb_sig);

    void setFb_sig_added(String fb_sig_added);

    void setFb_sig_api_key(String fb_sig_api_key);

    void setFb_sig_expires(String fb_sig_expires);

    void setFb_sig_friends(String fb_sig_friends);

    void setFb_sig_profile_update_time(String fb_sig_profile_update_time);

    void setFb_sig_session_key(String fb_sig_session_key);

    void setFb_sig_time(String fb_sig_time);

    void setFb_sig_user(String fb_sig_user);
}
