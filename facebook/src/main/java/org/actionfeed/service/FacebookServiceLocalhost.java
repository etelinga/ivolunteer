package org.actionfeed.service;


import com.google.code.facebookapi.schema.Album;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class FacebookServiceLocalhost extends FacebookService {

    private CacheService cacheService;

    public static final String ALBUMS = "Albums";

    public FacebookServiceLocalhost() {
    }

    /**
     * @param sessionKey The session key assigned to this user.
     * @return The id of the logged in user.
     */
    @Override public List<Album> getPhotoAlbums(String sessionKey) {
        ArrayList<Album> result = null;
        result = (ArrayList<Album>) cacheService.get(sessionKey, ALBUMS);
        if(null == result) {
            result = new ArrayList<Album>();
            Album album = new Album();
            album.setName(String.format("TestAlbum - %d",new Date().getTime()));
            album.setAid(1L);
            result.add(album);
            album = new Album();
            album.setName("Test Album Two");
            album.setAid(2L);
            result.add(album);
            cacheService.put(sessionKey, ALBUMS, result);
        }
        return result;
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }
}
