package org.actionfeed.domain.model;

import org.actionfeed.util.KeyFieldEncoder;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.persistence.Transient;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides common functionality for all model objects used within the framework.
 */
public abstract class BaseModel implements Model {

    /**
     * Constructor BaseModel creates a new BaseModel instance.
     */
    protected BaseModel() {
    }

    /**
     * Compares object equality. When using Hibernate, the primary key should
     * not be a part of this comparison.
     *
     * @param o object to compare to
     * @return true/false based on equality tests
     */
    @Override public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     *
     * @return hashCode
     */
    @Override public abstract int hashCode();

    /**
     * Method getVersion returns the version of this BaseModel object.
     * @return the version (type Integer) of this BaseModel object.
     */
    public abstract Integer getVersion();

    /**
     * A generic method to add a child model element in one of the containing relationships. It detects the container
     * based on the the TemporalModelSet type based on Java Generics. If there isn't a ITemporalModelSet defined for
     * the given model element and exception is thrown.
     *
     * @param model The model to add to the parent's set.
     * @return the add status.
     */
    public boolean addChild(BaseModel model) {
        if (null == model) {
            throw new IllegalArgumentException("A model must be provided.");
        }

        Set<BaseModel> set = getSetForModel(model);

        if (null == set) {
            throw new RuntimeException("A Set couldn't be found when adding the child model element " + model.toString());
        }

        if (model instanceof ChildModel) {
            ((ChildModel) model).setParent(this);
        }

        return set.add(model);
    }


    /**
     * Based on a given model object locate the relationship collection that holds the elements. Using this
     * to add child elements to an existing set.
     *
     * @param model The object to be placed into a <tt>Set</tt>
     * @return The set to used to place the given model object or null if no container was found for the given object.
     */
    @SuppressWarnings("unchecked")
    protected Set<BaseModel> getSetForModel(BaseModel model) {
        Set<BaseModel> result = null;

        PropertyDescriptor[] setProperites = BeanUtils.getPropertyDescriptors(this.getClass());
        for (PropertyDescriptor propertyDescriptor : setProperites) {
            Method method = propertyDescriptor.getReadMethod();
            Type type = method.getGenericReturnType();
            if (type != null && type instanceof ParameterizedTypeImpl) {
                Type types[] = ((ParameterizedTypeImpl) type).getActualTypeArguments();
                // This seems to be the only way to determine the Generics definition.
                if (types[0].toString().equals(model.getClass().toString())) {
                    // We found the container for the model element. See if it's initialized.
                    try {
                        result = (Set<BaseModel>) method.invoke(this, new Object[0]);
                        if (null == result || !Hibernate.isInitialized(result)) {
                            Method writeMethod = propertyDescriptor.getWriteMethod();
                            result = new HashSet<BaseModel>();
                            writeMethod.invoke(this, result);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Child [" + model.getClass().toString() +
                            "] not defined find for [" + this.getClass().toString() + "]");
                    }
                }
            }
        }
        return result;
    }

    /**
     * This field is marked transient because it isn't persisted. The key field is used
     * to rehydrate an object upon resubmission to the web application. 
     * @return An encoded string containing id:version to be sent to the client.
     */
    @Transient
    public String getKeyField() {
        return KeyFieldEncoder.getKeyField(getClass(), (Long) getPrimaryKey(), getVersion());
    }

}
