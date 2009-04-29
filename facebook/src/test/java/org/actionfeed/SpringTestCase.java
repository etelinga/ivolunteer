package org.actionfeed;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * Defines a class that can be used for the definition of beans
 * used within the spring framework that are tested by the JUnit framework.
 *
 * @author E. Telingator
 * @since
 *
 */
public class SpringTestCase extends TestCase {

    protected static final String BASE_CONTEXT = "applicationContext-test.xml";

    protected Log logger = LogFactory.getLog(getClass());

    protected AbstractApplicationContext ctx;

    /**
     * Loads the context so that the approprate beans can be configured
     * for testing.
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        if(ctx == null) {
            logger.trace("Getting initial context");
            ctx = new ClassPathXmlApplicationContext(getApplicationContextFileName());
            PropertyPlaceholderConfigurer config = (PropertyPlaceholderConfigurer) ctx.getBean("propertyConfigurer");
            assertNotNull(config);
        }
    }

    public ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * Override this method to use a different application context for different testing scenerios.
     * @return The file name to load for the context.
     */
    public String getApplicationContextFileName() {
        return BASE_CONTEXT;
    }

    /**
     * Get the session factory for attaching to the thread.
     * @return the session factory
     */
    protected SessionFactory getSessionFactory() {
        return (SessionFactory) ctx.getBean("sessionFactory");
    }

    /**
     * Open a session factory for the current test so that a single transaction spans the entire test.
     */
    protected void openSession() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    }

    /**
     * Close the current session that is attached to the current thread.
     */
    protected void closeSession() {
        SessionFactory sessionFactory = getSessionFactory();
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        if(null != sessionHolder) {
            sessionHolder.getSession().flush();
            sessionHolder.getSession().close();
            SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);
        }
    }

    /**
     * Close and reopen a session and attach it to the thread.
     */
    protected void restartSession() {
        closeSession();
        openSession();
    }

}
