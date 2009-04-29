package org.actionfeed.service;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.schema.Album;
import com.google.code.facebookapi.schema.PhotosGetAlbumsResponse;
import static org.actionfeed.Constants.API_KEY;
import static org.actionfeed.Constants.SECRET_KEY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.List;

/**
 * This class uses the Facebook client to provide access to the FB API.
 */
public class FacebookService  {

    protected MessageSource messageSource;

    protected static final Logger logger = LoggerFactory.getLogger(FacebookService.class);

    public FacebookService() {
    }

    /**
     * @param sessionKey The session key assigned to this user.
     * @return The id of the logged in user.
     */
    public Long getUserId(final String sessionKey) {
        if(null != sessionKey) {
            FacebookJaxbRestClient client = new FacebookJaxbRestClient(API_KEY, SECRET_KEY, sessionKey);
            try {
                return client.users_getLoggedInUser();
            } catch (FacebookException e) {
                logger.error("Facebook error: [{}]:[{}]",e.getCode(), e.getMessage());
            }
        }
        return null;
    }


    /**
     * @param sessionKey The session key assigned to this user.
     * @return The id of the logged in user.
     */
    public List<Album> getPhotoAlbums(final String sessionKey) {
        if(null != sessionKey) {
            FacebookJaxbRestClient client = new FacebookJaxbRestClient(API_KEY, SECRET_KEY, sessionKey);
            try {
                long userId = client.users_getLoggedInUser();
                PhotosGetAlbumsResponse albums = (PhotosGetAlbumsResponse) client.photos_getAlbums(userId);
                return albums.getAlbum();
            } catch (FacebookException e) {
                logger.error("Facebook error: [{}]:[{}]",e.getCode(), e.getMessage());
            }
        }
        return null;
    }


    /**
     * Set the MessageSource that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param messageSource message sourceto be used by this object
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
