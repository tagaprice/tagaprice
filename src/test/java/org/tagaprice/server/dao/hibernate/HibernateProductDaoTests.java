package org.tagaprice.server.dao.hibernate;

import org.springframework.test.context.ContextConfiguration;
import org.tagaprice.server.dao.hibernate.HibernateProductDAO;
import org.tagaprice.server.dao.interfaces.AbstractProductDaoTests;

/**
 * Unit tests for the {@link HibernateProductDAO}.
 * 
 * Runs all unit test with the spring configuration of this class.
 * 
 * @author haja
 * 
 */
@ContextConfiguration //this loads the xml file with the same name in the same directory as the class from the resources
public class HibernateProductDaoTests extends AbstractProductDaoTests { }