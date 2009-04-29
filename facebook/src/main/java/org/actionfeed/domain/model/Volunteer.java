package org.actionfeed.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Named Queries
 */
@Entity
@org.hibernate.annotations.NamedQueries({
    @org.hibernate.annotations.NamedQuery(
        name = "findVolunteer",
        query = "from Volunteer v where v.id = :facebookId",
        cacheable = false
    ),
    @org.hibernate.annotations.NamedQuery(
        name = "findVolunteerProfile",
        query = "from Volunteer v where v.emailAddress = :email",
        cacheable = false
    )})
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Volunteer extends BaseModel {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 179227202835815622L;
    /**
     * The default album within FB where photos are stored
     */
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean applicationAdded;
    @Column(nullable = true, length = 200)
    private String emailAddress;
    /**
     * Facebook id's will soon be 64 bit. Check to see how long the string version becomes.
     */
    @Column(nullable = false, unique = true, length = 80)
    private String facebookId;
    @Id
    private Long id;
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean infiniteSession;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean notifyFriends;
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean historyVisible;
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean enableComments;
    private Integer distance;
    @Column(length = 20)
    private String postalCode;
    @Temporal(TemporalType.TIMESTAMP)
    private Date profileUpdateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionExpiryDate;
    @Column(length = 80)
    private String sessionKey;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "volunteer")
    @org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Event> event = new HashSet<Event>();
    @Version
    private Integer version;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructor Volunteer creates a new Volunteer instance.
     */
    public Volunteer() {
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return The primary key for the model.
     */
    @Override @Transient
    public Serializable getPrimaryKey() {
        return getId();
    }

    /**
     * Method setFacebookId sets the facebookId of this Volunteer object.
     *
     * @param facebookId the facebookId of this Volunteer object.
     */
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
        try {
            id = Long.parseLong(facebookId);
        } catch (Exception e) {
            id = -1L;
        }
    }

    /**
     * @param key The primary key for the class.
     */
    public void setPrimaryKey(Serializable key) {
        setId((Long) key);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Method getApplicationAdded returns the applicationAdded of this Volunteer object.
     *
     * @return the applicationAdded (type Boolean) of this Volunteer object.
     */
    public Boolean getApplicationAdded() {
        return applicationAdded;
    }

    /**
     * Method setApplicationAdded sets the applicationAdded of this Volunteer object.
     *
     * @param applicationAdded the applicationAdded of this Volunteer object.
     */
    public void setApplicationAdded(Boolean applicationAdded) {
        this.applicationAdded = applicationAdded;
    }

    /**
     * Method getEmailAddress returns the emailAddress of this Volunteer object.
     *
     * @return the emailAddress (type String) of this Volunteer object.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Method setEmailAddress sets the emailAddress of this Volunteer object.
     *
     * @param emailAddress the emailAddress of this Volunteer object.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Method getFacebookId returns the facebookId of this Volunteer object.
     *
     * @return the facebookId (type String) of this Volunteer object.
     */
    public String getFacebookId() {
        return facebookId;
    }

    /**
     * Method getId returns the id of this Volunteer object.
     *
     * @return the id (type Long) of this Volunteer object.
     */
    public Long getId() {
        return id;
    }

    /**
     * Method setId sets the id of this Volunteer object.
     *
     * @param id the id of this Volunteer object.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method getInfiniteSession returns the infiniteSession of this Volunteer object.
     *
     * @return the infiniteSession (type Boolean) of this Volunteer object.
     */
    public Boolean getInfiniteSession() {
        return infiniteSession;
    }

    /**
     * Method setInfiniteSession sets the infiniteSession of this Volunteer object.
     *
     * @param infiniteSession the infiniteSession of this Volunteer object.
     */
    public void setInfiniteSession(Boolean infiniteSession) {
        this.infiniteSession = infiniteSession;
    }

    /**
     * Method getLastLogin returns the lastLogin of this Volunteer object.
     *
     * @return the lastLogin (type Date) of this Volunteer object.
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Method setLastLogin sets the lastLogin of this Volunteer object.
     *
     * @param lastLogin the lastLogin of this Volunteer object.
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Method getNotifyFriends returns the notifyFriends of this Volunteer object.
     *
     * @return the notifyFriends (type Boolean) of this Volunteer object.
     */
    public Boolean getNotifyFriends() {
        return notifyFriends;
    }

    /**
     * Method setNotifyFriends sets the notifyFriends of this Volunteer object.
     * @param notifyFriends the notifyFriends of this Volunteer object.
     */
    public void setNotifyFriends(Boolean notifyFriends) {
        this.notifyFriends = notifyFriends;
    }

    /**
     * Method getEnableComments returns the enableComments of this Volunteer object.
     * @return the enableComments (type Boolean) of this Volunteer object.
     */
    public Boolean getEnableComments() {
        return enableComments;
    }

    /**
     * Method setEnableComments sets the enableComments of this Volunteer object.
     * @param enableComments the enableComments of this Volunteer object.
     *
     */
    public void setEnableComments(Boolean enableComments) {
        this.enableComments = enableComments;
    }


    /**
     * Method getProfileUpdateDate returns the profileUpdateDate of this Volunteer object.
     *
     * @return the profileUpdateDate (type Date) of this Volunteer object.
     */
    public Date getProfileUpdateDate() {
        return profileUpdateDate;
    }

    /**
     * Method setProfileUpdateDate sets the profileUpdateDate of this Volunteer object.
     *
     * @param profileUpdateDate the profileUpdateDate of this Volunteer object.
     */
    public void setProfileUpdateDate(Date profileUpdateDate) {
        this.profileUpdateDate = profileUpdateDate;
    }

    /**
     * Method getSessionExpiryDate returns the sessionExpiryDate of this Volunteer object.
     *
     * @return the sessionExpiryDate (type Date) of this Volunteer object.
     */
    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }

    /**
     * Method setSessionExpiryDate sets the sessionExpiryDate of this Volunteer object.
     *
     * @param expires the sessionExpiryDate of this Volunteer object.
     */
    public void setSessionExpiryDate(Date expires) {
        this.sessionExpiryDate = expires;
    }

    /**
     * Method getSessionKey returns the sessionKey of this Volunteer object.
     *
     * @return the sessionKey (type String) of this Volunteer object.
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * Method setSessionKey sets the sessionKey of this Volunteer object.
     *
     * @param sessionKey the sessionKey of this Volunteer object.
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * Method getHistoryVisible returns the historyVisible of this Volunteer object.
     *
     * @return the historyVisible (type Boolean) of this Volunteer object.
     */
    public Boolean getHistoryVisible() {
        return historyVisible;
    }

    /**
     * Method setHistoryVisible sets the historyVisible of this Volunteer object.
     *
     * @param historyVisible the historyVisible of this Volunteer object.
     */
    public void setHistoryVisible(Boolean historyVisible) {
        this.historyVisible = historyVisible;
    }

    /**
     * Method getDistance returns the distance of this Volunteer object.
     *
     * @return the distance (type Integer) of this Volunteer object.
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Method setDistance sets the distance of this Volunteer object.
     *
     * @param distance the distance of this Volunteer object.
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Method getPostalCode returns the postalCode of this Volunteer object.
     *
     * @return the postalCode (type String) of this Volunteer object.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Method setPostalCode sets the postalCode of this Volunteer object.
     *
     * @param postalCode the postalCode of this Volunteer object.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method getEvent returns the event of this Volunteer object.
     *
     * @return the event (type Set<Event>) of this Volunteer object.
     */
    public Set<Event> getEvent() {
        return event;
    }

    /**
     * Method setEvent sets the event of this Volunteer object.
     *
     * @param event the event of this Volunteer object.
     */
    public void setEvent(Set<Event> event) {
        this.event = event;
    }

    /**
     * Method getVersion returns the version of this Volunteer object.
     *
     * @return the version (type Integer) of this Volunteer object.
     */
    @Override public Integer getVersion() {
        return version;
    }

    /**
     * Method setVersion sets the version of this Volunteer object.
     *
     * @param version the version of this Volunteer object.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

// ------------------------ CANONICAL METHODS ------------------------

    /**
     * Method equals ...
     *
     * @param o of type Object
     * @return boolean
     */
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer account = (Volunteer) o;
        return facebookId.equals(account.facebookId);
    }

    /**
     * Method hashCode ...
     *
     * @return int
     */
    @Override public int hashCode() {
        return facebookId.hashCode();
    }

    /**
     * Method toString ...
     *
     * @return String
     */
    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Member");
        sb.append(", id=").append(id);
        sb.append(", sessionKey='").append(sessionKey).append('\'');
        sb.append(", applicationAdded=").append(applicationAdded);
        sb.append(", facebookId='").append(facebookId).append('\'');
        sb.append(", infiniteSession=").append(infiniteSession);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", notifyFriends=").append(notifyFriends);
        sb.append(", profileUpdateDate=").append(profileUpdateDate);
        sb.append(", sessionExpiryDate=").append(sessionExpiryDate);
        sb.append(", version=").append(version);
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
