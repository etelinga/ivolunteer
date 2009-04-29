package org.actionfeed.web.model;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.Range;

/**
 * Form model supporting the Volunteer preferences.
 */
public class ProfileFormModel extends BaseFormModel {
// ------------------------------ FIELDS ------------------------------

    private Boolean notifyFriends = true;
    private Boolean enableComments = true;
    private Boolean historyVisible = true;
    @Range(min=1, max=1000, message="{distance.range}")
    private int distance = 1;
    @Length(min=1, max=50, message="{comment.short}" )
    private String postalCode;
    @Email
    private String emailAddress;
    private static final long serialVersionUID = -1013807319352307053L;

// --------------------------- CONSTRUCTORS ---------------------------

    public ProfileFormModel() {
        emailAddress = "a@b.c";
    }

// --------------------- GETTER / SETTER METHODS ---------------------


    public Boolean getNotifyFriends() {
        return notifyFriends;
    }

    public void setNotifyFriends(Boolean notifyFriends) {
        if(null != notifyFriends)
            this.notifyFriends = notifyFriends;
    }

    public Boolean getEnableComments() {
        return enableComments;
    }

    public void setEnableComments(Boolean enableComments) {
        if(null != enableComments)
            this.enableComments = enableComments;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getHistoryVisible() {
        return historyVisible;
    }

    public void setHistoryVisible(Boolean historyVisible) {
        this.historyVisible = historyVisible;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return True if we are in add mode.
     */
    public boolean isAdd() {
        return null != getReferrer() && getReferrer().toLowerCase().startsWith("add");
    }

    // ------------------------ CANONICAL METHODS ------------------------

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ProfileFormModel");
        sb.append("{notifyFriends=").append(notifyFriends);
        sb.append(", enableComments=").append(enableComments);
        sb.append(", historyVisible=").append(historyVisible);
        sb.append(", distance=").append(distance);
        sb.append(", postalCode='").append(postalCode).append('\'');
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
