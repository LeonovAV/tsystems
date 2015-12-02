package com.tsystems.rts.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.entities.Schedule;
import com.tsystems.rts.entities.Station;
import com.tsystems.rts.entities.Ticket;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.entities.Worker;

/**
 * Class contains methods for common operations with Hibernate session factory, sessions and transactions
 * @author Anton
 * @version 0.0.1
 *
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	
	static {
		try {
			Configuration configuration = configureHibernate();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (HibernateException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 	 
	 * @return
	 */
	public static Session beginTransaction() throws DAOException {
		try {
			Session hibernateSession = HibernateUtil.getSession();
			hibernateSession.beginTransaction();
			return hibernateSession;
		} catch (HibernateException e) {
			throw new DAOException("Database connection refused", e);
		}
	}
	 
	/**
	 * 
	 */
	public static void commitTransaction() throws DAOException {
		try {
			HibernateUtil.getSession().getTransaction().commit();
		} catch (HibernateException e) {
			throw new DAOException("Database connection refused", e);
		}
	}
	
	/**
	 * 
	 */
	public static void rollbackTransaction() throws DAOException {
		try {
			HibernateUtil.getSession().getTransaction().rollback();
		} catch (HibernateException e) {
			throw new DAOException("Database connection refused", e);
		}
	}
	 
	/**
	 * 
	 */
	public static void closeSession() throws DAOException {
		try {
			HibernateUtil.getSession().close();
		} catch (HibernateException e) {
			throw new DAOException("Database connection refused", e);
		}
	}
	 
	/**
	 * 
	 * @return
	 */
	public static Session getSession() throws DAOException {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			throw new DAOException("Database connection refused", e);
		}
	}
	
	/**
	 * 
	 */
	public static void closeSessionFactory() throws DAOException {
		try {
			sessionFactory.close();
		} catch (HibernateException e) {
			throw new DAOException("Database connection refused", e);
		}
	}
	
	private static Configuration configureHibernate() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(Passenger.class);
		configuration.addAnnotatedClass(Ticket.class);
		configuration.addAnnotatedClass(Train.class);
		configuration.addAnnotatedClass(Station.class);
		configuration.addAnnotatedClass(Schedule.class);
		configuration.addAnnotatedClass(Worker.class);
		return configuration;
	}
}
