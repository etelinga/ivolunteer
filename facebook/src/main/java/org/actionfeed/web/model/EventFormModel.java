package org.actionfeed.web.model;

/**
 *
 */
public class EventFormModel extends BaseFormModel {

//    @Length(min=2,max=400,message="{description.short}" )
    private String description;
    private String uri;
    private String vendor;
    private Long id;

    private static final long serialVersionUID = 6144170767884028169L;

    public EventFormModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(final String vendor) {
        this.vendor = vendor;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("StuffFormModel");
        sb.append("{description='").append(description).append('\'');
        sb.append(", uri='").append(uri).append('\'');
        sb.append(", vendor='").append(vendor).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        sb.append(super.toString());
        return sb.toString();
    }
}
