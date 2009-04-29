package org.actionfeed.domain.model;

import java.io.Serializable;

/**
 *  A basic interface that all model elements should support.
 */
public interface Model extends Serializable {
    /**
     * @return The primary key for the model.
     */
    public Serializable getPrimaryKey();

}
