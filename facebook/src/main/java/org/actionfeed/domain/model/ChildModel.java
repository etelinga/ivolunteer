package org.actionfeed.domain.model;

/**
 *  This class is used to mark a model that has a parent class in the object hierarchy.
 */
public interface ChildModel {
    /**
     * Set the parent of the this child model element. This method is called
     * when placing the element in a Set of the parent ensuring that the child
     * is properly connected to the parent.
     * @param parent The owning BaseModel element.
     */
    void setParent(BaseModel parent);
}
