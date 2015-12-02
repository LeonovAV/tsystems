package com.tsystems.rts.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.tsystems.rts.utils.DAOException;
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
		try {
			HibernateUtil.closeSessionFactory();
		} catch (DAOException e) {
			Logger.getLogger("LOG-FILE-APPENDER").fatal("Can not close connection to the DB", e);
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// Initialize Hibernate session factory
		try {
			HibernateUtil.getSessionFactory();
		} catch (ExceptionInInitializerError e) {
			Logger.getLogger("LOG-FILE-APPENDER").fatal("Can not initialize connection to the DB");
			// Set attribute to the context, which indicates a problem with DB
			arg0.getServletContext().setAttribute("dbError", "Can not initialize connection to the DB");
		}
		
	}

}
