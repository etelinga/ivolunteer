package org.actionfeed.web.model;

import org.actionfeed.Constants;

import java.util.regex.Pattern;

/**
 *
 */
public class BaseFormModel extends BaseFacebookModel {

    private static final Pattern PATTERN = Pattern.compile("/");

    private String keyField;
    private Integer version;
    private String referrer;
    private String referrerURI;

    public BaseFormModel() {
    }

    /**
     * Used to determine we are editing a field v.s. a first time display since all FB requests are POSTS.
     * The same constant is used in the base controller.
     * @return A constant string for placement in a hidden field in a form.
     */
    public String getFormId() {
        return Constants.FORM_EDIT;
    }

    public String getKeyField() {
        return keyField;
    }

    /**
     * @param keyField The key data field hidden in a form produces the id and version number of the form.
     */
    public void setKeyField(final String keyField) {
        this.keyField = keyField;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getReferrerURI() {
        return referrerURI;
    }

    public void setReferrerURI(String referrerURI) {
        this.referrerURI = referrerURI;
        String[] strings = PATTERN.split(referrerURI);
        if(null != strings && strings.length >0) {
            this.referrer = strings[strings.length - 1];
        }
    }

    /**
     * Method toString ...
     * @return String
     */
    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{keyField='").append(keyField).append('\'');
        sb.append("{version='").append(version).append('\'');
        sb.append("{referrer='").append(referrer).append('\'');
        sb.append("{referrerURI='").append(referrerURI).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
