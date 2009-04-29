package org.actionfeed;

import org.actionfeed.facebook.api.FacebookRequestParameters;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSource;

/**
 *
 */
public final class Constants implements MessageSourceAware {

    private MessageSource messageSource_;

/*
    //iVolunteer keys
    public static final String API_KEY = "6a210ee17ee03052e04d2cf707d181e1";

    public static final String SECRET_KEY = "c77198004b1e6fcd48108830e2687f1a";
*/

    // pms keys
    public static final String API_KEY = "6a210ee17ee03052e04d2cf707d181e1";

    public static final String SECRET_KEY = "c77198004b1e6fcd48108830e2687f1a";

    public static final String APPLICATION_ID = "44098838333";

    public static final String FACEBOOK_LOGIN_URL = String.format("http://www.facebook.com/login.php?api_key=%s&amp;v=1.0&amp;next=home.xhtml", API_KEY);

    public static final String FACEBOOK_INSTALL_URL = String.format("http://www.facebook.com/add.php?api_key=%s", API_KEY);

    public static final String FACEBOOK_INFINITE_URL = String.format("http://www.facebook.com/code_gen.php?v=1.0&api_key=%s", API_KEY);
    /** 
     * todo Move this somewhere appropriate. This needs to be a POST to confirm the logout
     */
    public static final String FACEBOOK_LOGOUT_URL = "http://www.facebook.com/logout.php?confirm=1";

    public static final String AUTH_TOKEN = FacebookRequestParameters.AUTH_TOKEN.toString();

    public static final String FB_SIGNATURE = FacebookRequestParameters.SIGNATURE.toString();

    public static final String SESSION_KEY = FacebookRequestParameters.SIGNATURE_SESSION_KEY.toString();

    public static final String FB_USER = FacebookRequestParameters.SIGNATURE_USER.toString();

    public static final String FB_INFINITE_SESSION_REQEUST = "infiniteRequest";

    public static final String CLIENT = "client";

    public static final String FACEBOOK_MODEL = "fbModel";

    public static final String MEMBER_ID = "memberMe";

    public static final String KEY_FIELD = "keyField";

    public static final String EVENT_LIST = "events";

    public static final String COMMENT_LIST = "comments";

    public static final String FORM_EDIT = "formId";

    public static final int PAGE_SIZE = 5;

    public static final int MAX_PAGES = 3;

    /**
     * Constructor Constants creates a new Constants instance.
     */
    public Constants() {
    }

    /**
     * Method getApiKey returns the apiKey of this Constants object.
     * @return the apiKey (type String) of this Constants object.
     */
    public String getApiKey() {
        return messageSource_.getMessage("API_KEY", null, API_KEY, null);
    }

    /**
     * Method getSecretKey returns the secretKey of this Constants object.
     * @return the secretKey (type String) of this Constants object.
     */
    public String getSecretKey() {
        return messageSource_.getMessage("SECRET_KEY", null, SECRET_KEY, null);
    }

    /**
     * Set the MessageSource that this object runs in.
     * <p/>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param messageSource message sourceto be used by this object
     */
    @Override public void setMessageSource(MessageSource messageSource) {
        messageSource_ = messageSource;
    }
}
