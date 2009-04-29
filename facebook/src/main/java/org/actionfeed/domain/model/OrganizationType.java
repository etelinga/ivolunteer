package org.actionfeed.domain.model;

import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.Column;
import java.io.Serializable;

/**
 *
 */
public class OrganizationType extends BaseModel {
    @Id
    private Long id;
    @Version
    private Integer version;
    @Column(nullable = false, unique = true, length = 40)
    private String name;

    /**
     * Constructor OrganizationType creates a new OrganizationType instance.
     */
    public OrganizationType() {
    }

    /**
     * Method getVersion returns the version of this OrganizationType object.
     * @return the version (type Integer) of this OrganizationType object.
     */
    @Override public Integer getVersion() {
        return version;
    }

    /**
     * Method setVersion sets the version of this OrganizationType object.
     * @param version the version of this OrganizationType object.
     *
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * {inheritDoc}
     */
    @Override public Serializable getPrimaryKey() {
        return id;
    }

    /**
     * Method getId returns the id of this OrganizationType object.
     * @return the id (type Long) of this OrganizationType object.
     */
    public Long getId() {
        return id;
    }

    /**
     * Method setId sets the id of this OrganizationType object.
     * @param id the id of this OrganizationType object.
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method equals ...
     *
     * @param o of type Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationType that = (OrganizationType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    /**
     * Method hashCode ...
     * @return int
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    /**
     * Method getName returns the name of this OrganizationType object.
     *
     * @return the name (type String) of this OrganizationType object.
     */
    public String getName() {
        return name;
    }

    /**
     * Method setName sets the name of this OrganizationType object.
     *
     * @param name the name of this OrganizationType object.
     *
     */
    public void setName(String name) {
        this.name = name;
    }
}
