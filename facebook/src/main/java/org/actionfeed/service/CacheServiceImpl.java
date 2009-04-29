package org.actionfeed.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements a short term cache elements for web based elements without resorting to the normal web session
 * management garbage. Should proxy this service for all DatabaseService saves to reset the member cache forcing
 * a database query for subsequent refreshes.
 */
public class CacheServiceImpl implements CacheService {

    protected static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    private EhCacheManagerFactoryBean ehCache;

    private static final String DEFAULT_CACHE_NAME = "memberSessionCache";

    private String cacheName = DEFAULT_CACHE_NAME;

    public CacheServiceImpl() {
    }

    /**
     * Removes all cache entries for the given memberId.
     * @param volunteerId The entry id to remove.
     */
    public void resetVolunteerCache(final String volunteerId) {
        logger.trace("Resetting cache for member [{}]",volunteerId);
        getCache().remove(getMemberKey(volunteerId));
    }

    /**
     * @param volunteerId Adds an element to the cache for the given member.
     * @param key The key to put the element under.
     * @param value The object to place.
     */
    @SuppressWarnings("unchecked")
    public void put(String volunteerId, String key, Object value) {
        logger.trace("Putting cache for member [{}]:[{}]",volunteerId, key);
        Element element = getElement(volunteerId);
        if(null != element) {
            Map values = (Map) element.getObjectValue();
            if(null == values) {
                values = new HashMap();
            }
            values.put(key, value);
        } else {
            Map values = new HashMap();
            values.put(key, value);
            putElement(volunteerId, values);
        }
    }

    /**
     * @param volunteerId Get an element from the cache for the given member.
     * @param key  The object key for the given member.
     * @return The cached element or null if not found.
     */
    public Object get(final String volunteerId, final String key) {
        Element element = getElement(volunteerId);
        if(null != element) {
            Map values = (Map) element.getObjectValue();
            logger.trace("Found cache element [{}] for member [{}]", key, volunteerId);
            return values.get(key);
        }
        return null;
    }

    /**
     * @param volunteerId Remove the given key for this member.
     * @param key The key of the element to remove.
     */
    public void remove(final String volunteerId, final String key) {
        logger.trace("Removing element [{}] for member [{}]",key, volunteerId);
        Element element = getElement(volunteerId);
        if(null != element) {
            Map values = (Map) element.getObjectValue();
            values.remove(key);
        }
    }

    /**
     * @param memberId The member id to get an element from the cache.
     * @return Either the element or null.
     */
    private Element getElement(final String memberId) {
        return getCache().get(getMemberKey(memberId));
    }

    /**
     * @param memberId Put a cache element based on the member id.
     * @param value Whatever needs to be stored in the cache.
     */
    private void putElement(final String memberId, final Object value) {
        getCache().put(new Element(getMemberKey(memberId), value));
    }

    /**
     * Cannot use the string as the key since it would appear that EhCache uses the interned value when storing
     * so that the same string will not be resolved on a get.
     * @param memberId The member id.
     * @return  A key to use for the cache.
     */
    private long getMemberKey(final String memberId) {
        return (long)memberId.hashCode();
    }

    private Cache getCache() {
        CacheManager cacheManager = (CacheManager) ehCache.getObject();
        if(null != cacheManager) {
            return cacheManager.getCache(cacheName);
        }
        return null;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public EhCacheManagerFactoryBean getEhCache() {
        return ehCache;
    }

    public void setEhCache(EhCacheManagerFactoryBean ehCache) {
        this.ehCache = ehCache;
    }
}
