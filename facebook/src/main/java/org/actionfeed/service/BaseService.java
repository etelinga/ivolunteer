package org.actionfeed.service;

import org.actionfeed.domain.dao.Dao;
import org.actionfeed.domain.dao.HibernateDao;
import org.actionfeed.domain.model.BaseModel;
import org.actionfeed.domain.model.Model;
import org.actionfeed.util.KeyFieldEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Contains the common service operations and should be extended by a implementation providing a service
 * to a controller.
 */
public class BaseService {
// ------------------------------ FIELDS ------------------------------

    protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected Dao dao;

    protected CacheService cacheService;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructor BaseService creates a new BaseService instance.
     */
    public BaseService() {
    }

    /**
     * Method getModel loads any of the model elements defined in the system based on the
     * primary key defined.
     *
     * @param clazz of type Class to load.
     * @param id the model to load.
     * @return BaseModel The database entry.
     */
    public BaseModel getModel(final Class clazz, final Serializable id) {
        return (BaseModel) dao.getModel(clazz, id);
    }

    /**
     * Based on the a generated keyField from a BaseModel reverse the key and load based on the class
     * name, id, and version fields encoded in the base model.
     * todo: This really belongs in BaseModel since the coupling is way too tight for this to be here!!!!
     * @param keyField The source string
     * @return The model represented by this key field that is parsed from the encoded key string.
     */
    public BaseModel getModelUsingKeyField(final String keyField) {
        if (StringUtils.hasText(keyField)) {
            Class<?> clazz = KeyFieldEncoder.getBaseModelFromKeyField(keyField);
            Long key = KeyFieldEncoder.getPrimaryKeyFromKeyField(keyField);
            Integer version = KeyFieldEncoder.getVersionFromKeyField(keyField);
            if(null != clazz && null != key) {
                BaseModel model = getModel(clazz, key);
                logger.trace("Found model for [{}]:[{}]", clazz.toString(), key);
                if (!model.getVersion().equals(version)) {
                    logger.trace("Version change on [{}] for id [{}] version [{}]:[{}]",
                        new Object[] {clazz.toString(), key, version, model.getVersion()});
                }
                return model;
            }
        }
        return null;
    }

    /**
     * Delete a model identified by class and primary key.
     * @param clazz The model of the class to delete
     * @param id The primary key to use for removal.
     */
    public void deleteModel(final Class clazz, final Serializable id) {
        dao.deleteModel(clazz, id);
    }

    /**
     * Delete a model using the information contained within the model.
     * @param model The model to delete.
     */
    public void deleteModel(final Model model) {
        dao.deleteModel(model);
    }

    /**
     * Delete a model using the standard key field.
     * @param keyField The key field not null.
     */
    public void deleteModel(final String keyField) {
        BaseModel model = getModelUsingKeyField(keyField);
        if (null != model) {
            logger.trace("Deleting model [{}]:[{}]", model.getClass().getSimpleName(), model.getPrimaryKey());
            dao.deleteModel(model);
        } else {
            logger.trace("Delete model not found [{}]", keyField);
        }
    }

    /**
     * Call when there are changes pending within a session that should be written to the disk.
     */
    public void flushChanges() {
        dao.flushChanges();
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Method getCacheService returns the cacheService of this BaseService object.
     * @return the cacheService (type CacheService) of this BaseService object.
     */
    public CacheService getCacheService() {
        return cacheService;
    }

    /**
     * Method setCacheService sets the cacheService of this BaseService object.
     * @param cacheService the cacheService of this BaseService object.
     *
     */
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
     * Method getHibernateDao returns the hibernateDao of this BaseService object.
     * @return the hibernateDao (type Dao) of this BaseService object.
     */
    public Dao getDao() {
        return dao;
    }

    /**
     * Method setHibernateDao sets the hibernateDao of this BaseService object.
     * @param dao the hibernateDao of this BaseService object.
     */
    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
