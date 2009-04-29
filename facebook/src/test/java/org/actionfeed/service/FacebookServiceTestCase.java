package org.actionfeed.service;

import org.actionfeed.SpringServiceTestCase;

import java.util.List;

import com.google.code.facebookapi.schema.Album;

/**
 *
 */
public class FacebookServiceTestCase extends SpringServiceTestCase {

    public static final String SESSION_KEY = "a4a5336dc2c5792562a8134d-641244912";

    public FacebookServiceTestCase() {
    }

    public void testGetUserId() throws Exception {
        Long id = facebookService.getUserId(SESSION_KEY);
        assertNotNull(id);
    }

    public void testGetPhotoAlbums() throws Exception {
        List<Album> list = facebookService.getPhotoAlbums(SESSION_KEY);
        if(null != list) {
            for (Album album : list) {
                System.out.println(album.getName());
            }
        }
    }

}
