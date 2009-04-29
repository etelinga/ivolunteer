package org.actionfeed.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Locale;

/**
 * Tries to load a property from the Tomcat JNDI resource. If the resource cannot be found then
 * the bundles are used to try to locate a default. If both resources don't have a value for
 * the property then null is returned.
 */
public class JndiPropertyResolver implements MessageSourceAware {

    protected static final Logger logger = LoggerFactory.getLogger(JndiPropertyResolver.class);

    public static String JNDI_BASE_ENVIRONMENT = "java:comp/env/";

    private MessageSource messageSource;

    public JndiPropertyResolver() {
    }

    /**
     * Try to obtain a parameter from the JNDI context. If it isn't available then try to
     * use the properties bundle to get the default. If it's not defined in either place then
     * null will be returned.
     * <p/>
     * Since we are starting with a JNDI reference this method will automatically convert from the message format
     * to the jndi format. e.g. mail.host becomes java:comp/env/mail/host.
     *
     * @param parameterName A property to find either in JNDI or the properties file using either format.
     * @param required      If a required parameter isn't found throw an exception and logger a message.
     * @return The value or null if no definition is required.
     */
    public Object getJndiOrDefaultParameter(String parameterName, boolean required) {
        if (null == parameterName) {
            throw new IllegalArgumentException("Missing parameter name");
        }

        String jndiResourceName = null;
        String propertyResourceName = null;

        // First check to see if we have a properties type parameter based on the location of a period.
        if (parameterName.indexOf('.') > -1) {
            String convertedJndi = parameterName.replace('.', '/');
            jndiResourceName = JNDI_BASE_ENVIRONMENT + convertedJndi;
            propertyResourceName = parameterName;
        } else {
            // See if we have a JNDI style parameter.
            if (parameterName.indexOf('/') > -1) {
                if (parameterName.indexOf(':') > -1) {
                    // We have a fully specified JNDI name therefore cannot determine bundle properties
                    jndiResourceName = parameterName;
                } else {
                    jndiResourceName = JNDI_BASE_ENVIRONMENT + parameterName;
                    propertyResourceName = parameterName.replace('/', '.');
                }
            } else {
                throw new IllegalArgumentException("Unknown argement type [" + parameterName + "]");
            }
        }

        logger.debug("Looking for JNDI variable [{}]",jndiResourceName);

        Object result = null;

        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        try {
            result = jndiObjectFactoryBean.getJndiTemplate().lookup(jndiResourceName);
        } catch (Exception e) {
            try {
                result = messageSource.getMessage(propertyResourceName, null, Locale.getDefault());
            } catch (Exception ex) {
                logger.warn("Unable to locate property [{}]",parameterName);
                if(required) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return result;
    }


    /**
     * Set the MessageSource that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param messageSource message sourceto be used by this object
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
