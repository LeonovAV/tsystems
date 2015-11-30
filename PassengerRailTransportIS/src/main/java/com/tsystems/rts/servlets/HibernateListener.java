package com.tsystems.rts.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tsystems.rts.utils.HibernateUtil;

/**
 * Add listener to connect to the database
 * @author Anton
 * @version 0.0.1
 *
 */
public class HibernateListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// Close Hibernate session factory after working
		HibernateUtil.closeSessionFactory();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// Initialize Hibernate session factory
		HibernateUtil.getSessionFactory();
	}

}
