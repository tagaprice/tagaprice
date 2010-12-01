package org.tagaprice.server.dao.postgres;

import org.junit.Before;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.tagaprice.server.dao.AbstractProductDaoTest;
import org.tagaprice.server.dao.hibernate.HibernateProductDAO;

/**
 * Unit tests for the {@link HibernateProductDAO}.
 * 
 * TODO "HibernateProductDAOTests-context.xml" determines the actual beans to test.
 * 
 * @author "haja"
 * 
 */
@ContextConfiguration
@DirtiesContext
public class HibernateProductDaoTests extends AbstractProductDaoTest { }