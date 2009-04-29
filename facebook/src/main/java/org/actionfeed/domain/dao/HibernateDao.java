package org.actionfeed.domain.dao;

import org.actionfeed.domain.model.Model;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.List;

/**
 * Defines common Dao functionality that spans both non-temporal and temporal dao operations.
 */
@Repository
public class HibernateDao implements Dao {

    protected SessionFactory sessionFactory;

    protected BasicDataSource dataSource;

    protected static final Logger logger = LoggerFactory.getLogger(HibernateDao.class);

    /**
     * Constructor HibernateDao creates a new HibernateDao instance.
     */
    public HibernateDao() {
    }

    /**
     * Method init ...
     */
    public void init() {
        if(null != dataSource) {
            logger.info("Connecting to [{}]:[{}]", dataSource.getUrl(), dataSource.getUsername());
        }
    }

    /**
     * Method saveModel ...
     *
     * @param model of type Model
     */
    public void saveModel(final Model model) {
        sessionFactory.getCurrentSession().saveOrUpdate(model);
    }

    /**
     * Method mergeModel ...
     *
     * @param model of type Model
     * @return Model
     */
    public Model mergeModel(final Model model) {
        return (Model) sessionFactory.getCurrentSession().merge(model);
    }

    /**
     * Method deleteModel ...
     *
     * @param model of type Model
     */
    public void deleteModel(final Model model) {
        sessionFactory.getCurrentSession().delete(model);
    }

    /**
     * Method deleteModel ...
     *
     * @param clazz of type Class
     * @param id of type Serializable
     */
    public void deleteModel(final Class clazz, final Serializable id) {
        Object model = getModel(clazz, id);
        if(null != model) {
            sessionFactory.getCurrentSession().delete(model);
        }
    }

    /**
     * Method flushChanges ...
     */
    public void flushChanges() {
        sessionFactory.getCurrentSession().flush();
    }

    /**
     * Method getModel ...
     *
     * @param clazz of type Class
     * @param key of type Serializable
     * @return Model
     */
    public Model getModel(final Class clazz, Serializable key) {
        return (Model) sessionFactory.getCurrentSession().get(clazz, key);
    }

    /**
     * Method createQuery ...
     *
     * @param query of type String
     * @return Query
     */
    public Query createQuery(String query) {
        logger.trace("Created Hibernate query {}", query);
        return sessionFactory.getCurrentSession().createQuery(query);
    }

    /**
     * Method createQueryByName ...
     *
     * @param name of type String
     * @return Query
     */
    public Query createQueryByName(String name) {
        logger.trace("Finding named query {}", name);
        return sessionFactory.getCurrentSession().getNamedQuery(name);
    }

    /**
     * Find matching model objects based on an example.
     * NOTE: If any of the strings contains a % then like is enabled for all fields.
     *
     * @param exampleModel The model containing the query components.
     * @return A list of matching model objects.
     */
    public List findByExample(Object exampleModel) {
        logger.trace("Query by example {}", exampleModel.toString());
        return executeCriteria(getCriteriaFromExample(exampleModel));
    }

    /**
     * Build up a standard criteria object for an example based query.
     *
     * @param exampleModel The sample model to use as the basis.
     * @return The criteria to use in Hibernate.
     */
    private DetachedCriteria getCriteriaFromExample(Object exampleModel) {
        Example example = getExample(exampleModel);
        DetachedCriteria criteria = DetachedCriteria.forClass(exampleModel.getClass());
        criteria.add(example);
        return criteria;
    }

    /**
     * Create a default Example for query purposed.
     *
     * @param exampleModel The example model.
     * @return A default populated example.
     */
    private Example getExample(Object exampleModel) {
        if (null == exampleModel) {
            throw new RuntimeException("Missing example object to query");
        }
        Example example = Example.create(exampleModel);
        example.excludeZeroes();
        if (example.toString().indexOf("%") > 0) {
            example.enableLike();
        }
        //Case-insensitive
        example.ignoreCase();
        return example;
    }

    /**
     * Execute the given criteria object against the database.
     *
     * @param detatchedCriteria The criteria object.
     * @return The list representing the results.
     */
    private List executeCriteria(DetachedCriteria detatchedCriteria) {
        Criteria criteria = detatchedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
        List results = criteria.list();
        logger.trace("Example found : [{}] elements", results.size());
        return results;
    }

    /**
     * Method getSessionFactory returns the sessionFactory of this HibernateDao object.
     *
     * @return the sessionFactory (type SessionFactory) of this HibernateDao object.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Method setSessionFactory sets the sessionFactory of this HibernateDao object.
     *
     * @param sessionFactory the sessionFactory of this HibernateDao object.
     *
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Method getDataSource returns the dataSource of this HibernateDao object.
     *
     * @return the dataSource (type BasicDataSource) of this HibernateDao object.
     */
    public BasicDataSource getDataSource() {
        return dataSource;
    }

    /**
     * Method setDataSource sets the dataSource of this HibernateDao object.
     *
     * @param dataSource the dataSource of this HibernateDao object.
     *
     */
    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
