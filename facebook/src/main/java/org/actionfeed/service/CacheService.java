package org.actionfeed.service;

/**
 *
 */
public interface CacheService {
    /**
     * Method resetMemberCache ...
     * @param memberId of type String
     */
    void resetVolunteerCache(String memberId);

    /**
     * Method put ...
     * @param volunteerId of type String
     * @param key of type String
     * @param value of type Object
     */
    void put(String volunteerId, String key, Object value);

    /**
     * Method gets the object for the member and key.
     * @param volunteerId of type String
     * @param key of type String
     * @return Object
     */
    Object get(String volunteerId,  String key);

    /**
     * Method removes the key for the given member id.
     * @param volunteerId of type String
     * @param key of type String
     */
    void remove(String volunteerId,  String key);
}
