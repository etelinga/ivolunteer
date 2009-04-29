package org.actionfeed.domain.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Represents an event.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.NamedQueries({
@org.hibernate.annotations.NamedQuery(
    name = "findMyEvents",
    query = "from Event e where e.id = :id and e.volunteer = :volunteer",
    cacheable = false
)
}
)
public class Event extends BaseModel implements ChildModel {
// ------------------------------ FIELDS ------------------------------

    @Column(nullable = false, length = 400)
    private String description;
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    private Volunteer volunteer;
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean publish;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(length = 256)
    private String uri;
    @Version
    private Integer version;
    private static final long serialVersionUID = -8961904401332056952L;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructor Event creates a new Event instance.
     */
    public Event() {
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return The primary key for the model.
     */
    @Override public Serializable getPrimaryKey() {
        return id;
    }

    /**
     * Set the parent of the this child model element. This method is called
     * when placing the element in a Set of the parent ensuring that the child
     * is properly connected to the parent.
     *
     * @param parent The owning BaseModel element.
     */
    @Override public void setParent(BaseModel parent) {
        volunteer = (Volunteer) parent;
    }

    /**
     * @param key The primary key for the class.
     */
    public void setPrimaryKey(Serializable key) {
        setId((Long) key);
    }

// --------------------- GETTER / SETTER METHODS ---------------------


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return description.equals(event.description);
    }

    @Override public int hashCode() {
        return description.hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Event");
        sb.append("{id=").append(id);
        sb.append(", version=").append(version);
        sb.append(", volunteer=").append(volunteer);
        sb.append(", description='").append(description).append('\'');
        sb.append(", uri='").append(uri).append('\'');
        sb.append(", updateDate=").append(updateDate);
        sb.append(", publish=").append(publish);
        sb.append('}');
        return sb.toString();
    }
}
