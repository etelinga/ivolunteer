package org.actionfeed.domain.dao;

import org.actionfeed.domain.model.Model;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.Serializable;
import java.util.List;

/**
 * The base interface for the DAO
 */
public interface Dao {
    /**
     * Method init ...
     */
    public void init();

    /**
     * Method saveModel ...
     *
     * @param model of type Model
     */
    public void saveModel(Model model);

    /**
     * Method mergeModel ...
     *
     * @param model of type Model
     * @return Model
     */
    public Model mergeModel(Model model);

    /**
     * Method deleteModel ...
     *
     * @param model of type Model
     */
    public void deleteModel(Model model);

    /**
     * Method deleteModel ...
     *
     * @param clazz of type Class
     * @param id of type Serializable
     */
    public void deleteModel(Class clazz, Serializable id);

    /**
     * Method flushChanges ...
     */
    public void flushChanges();

    /**
     * Method getModel ...
     *
     * @param clazz of type Class
     * @param key of type Serializable
     * @return Model
     */
    public Model getModel(Class clazz, Serializable key);

    /**
     * Method findByExample ...
     *
     * @param exampleModel of type Object
     * @return List
     */
    public List findByExample(Object exampleModel);

    /**
     * Method getSessionFactory returns the sessionFactory of this Dao object.
     *
     * @return the sessionFactory (type SessionFactory) of this Dao object.
     */
    public SessionFactory getSessionFactory();

    /**
     * Method setSessionFactory sets the sessionFactory of this Dao object.
     *
     * @param sessionFactory the sessionFactory of this Dao object.
     *
     */
    public void setSessionFactory(SessionFactory sessionFactory);

    /**
     * Method getDataSource returns the dataSource of this Dao object.
     *
     * @return the dataSource (type BasicDataSource) of this Dao object.
     */
    public BasicDataSource getDataSource();

    /**
     * Method setDataSource sets the dataSource of this Dao object.
     *
     * @param dataSource the dataSource of this Dao object.
     *
     */
    public void setDataSource(BasicDataSource dataSource);

    /**
     * Method createQueryByName Query the database based on the annotation defined name.
     * @param name of query
     * @return Query The query to obtain results.
     */
    public Query createQueryByName(String name);

}
