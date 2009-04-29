package org.actionfeed.web.spring;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.FacebookSignatureUtil;
import static org.actionfeed.Constants.*;
import org.actionfeed.service.ApplicationService;
import org.actionfeed.web.model.BaseFacebookModel;
import org.apache.commons.lang.StringUtils;
import static org.apache.commons.lang.StringUtils.isBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Intercepts all incoming requests and either redirects the user to logon using Facebook or
 * validates the incomming request and loads the user session information from the cache to
 * create a new FaceBookClient for use in this request and parses the extension information
 * to direct to the appropriate action within the framework.
 * <p/>
 * The first step determines if there is an
 */
public class FacebookInterceptor extends HandlerInterceptorAdapter {

    private ApplicationService service;

    protected static final Logger logger = LoggerFactory.getLogger(FacebookInterceptor.class);

    /**
     * Constructor FacebookInterceptor creates a new FacebookInterceptor instance.
     */
    public FacebookInterceptor() {
    }

    /**
     * This method determines the source of the request and attempts to find the user's information
     * within the cache.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        logger.trace("Validating FB request");

        BaseFacebookModel facebookModel = new BaseFacebookModel();

        String serverName = request.getServerName();
        if (serverName != null && serverName.contains("localhost")) {
            logger.trace("Running localhost");
            facebookModel.setFb_in_canvas("1");
            facebookModel.setFb_sig("38d218383b7608ad4e395e2efbd688b4");
            facebookModel.setFb_sig_added("0");
            facebookModel.setFb_sig_api_key("ff962391a037e751134e93e005282fea");
            facebookModel.setFb_sig_expires("1194146685");
            facebookModel.setFb_sig_friends("552916988,622552735,633221973,653969472,719754054");
            facebookModel.setFb_sig_profile_update_time("1193412664");
            facebookModel.setFb_sig_session_key("1c1ebaee81aa1f42048de32d-izxTBgLjpGQ0yVaKaIJ2mzQ..");
            facebookModel.setFb_sig_time("1194060349.0918");
            facebookModel.setFb_sig_user("641244912");
            request.setAttribute(FACEBOOK_MODEL, facebookModel);
            updateMember(facebookModel);
            WebUtils.setSessionAttribute(request, SESSION_KEY, "LOCALHOST");

            // The sessionKey isn't included in the request to prevent FacebookClient calls.
            return true;
        }

        /** Facebook sends parameter to ensure that it's a request from their server */
        String signature = request.getParameter(FB_SIGNATURE);
        if (null != signature) {
            Map<String, String> signatures = FacebookSignatureUtil.
                extractFacebookParamsFromArray(request.getParameterMap());

            if (!FacebookSignatureUtil.verifySignature(signatures, SECRET_KEY)) {
                logger.warn("Invalid signature found!");
                return false;
            }
        }

        // First look for the session key.
        String sessionKey = request.getParameter(SESSION_KEY);
        if (isBlank(sessionKey)) {
            String authToken = request.getQueryString();
            if (null != authToken && authToken.contains(AUTH_TOKEN)) {
                logger.trace("Query String [{}]",authToken);
                // Making this robust in case FB changes stuff.
                String tokens[] = StringUtils.split(authToken, "=&");
                String token = null;
                if(tokens.length > 0) {
                    token = tokens[1];
                }

                if (null != token) {
                    facebookModel.setAuth_token(token);
                    logger.trace("Creating client with AUTH_TOKEN [{}]",token);
                    FacebookJaxbRestClient restClient = new FacebookJaxbRestClient(API_KEY, SECRET_KEY);
                    restClient.setIsDesktop(false);
                    try {
                        sessionKey = restClient.auth_getSession(token);
                        facebookModel.setFb_sig_session_key(sessionKey);
                        long userId = restClient.users_getLoggedInUser();
                        // todo update this code to use longs.
                        facebookModel.setFb_sig_user(Long.toString(userId));
                        logger.trace("First session, new user, values = [{}]",facebookModel.toString());
                    } catch (FacebookException fe) {
                        logger.error("Facebook Client Error [{}]:[{}]", fe.getCode(), fe.getMessage());
                    }
                } else {
                    logger.trace("Split auth token [%s] missing and no session key",authToken);
                }
            } else {
                logger.trace("No AUTH_TOKEN");
            }
        }

        logger.trace("Checking session key [{}]", (null != sessionKey) ? sessionKey : "Not Found");

        if (!isBlank(sessionKey)) {
            WebUtils.setSessionAttribute(request, SESSION_KEY, sessionKey);
            ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(facebookModel);
            dataBinder.bind(request);
            request.setAttribute(FACEBOOK_MODEL, facebookModel);
            updateMember(facebookModel);
            return true;
        }

        // In the case of post we need draw a canvas page for the user otherwise, we'll redirect.
        if(request.getMethod().equals("POST")) {
            logger.trace("Got a POST, continuing with processing.");
            return true;
        }

        logger.debug("Sending FB redirect:" + FACEBOOK_LOGIN_URL);
        response.sendRedirect(FACEBOOK_LOGIN_URL);
        return false;
    }

    /**
     * Method getService returns the service of this FacebookInterceptor object.
     * @return the service (type ApplicationService) of this FacebookInterceptor object.
     */
    public ApplicationService getService() {
        return service;
    }

    /**
     * Method setService sets the service of this FacebookInterceptor object.
     * @param service the service of this FacebookInterceptor object.
     *
     */
    public void setService(ApplicationService service) {
        this.service = service;
    }

    /**
     * Update the database with any new information provided by the request.
     *
     * @param model The model obtained from the request.
     */
    private void updateMember(BaseFacebookModel model) {
        logger.debug("Saving model [{}]", model.toString());
        service.saveVolunteer(model);
//      Added this when an update error occurred, fixed the problem but may not be needed[service.flushChanges();]
    }
}
