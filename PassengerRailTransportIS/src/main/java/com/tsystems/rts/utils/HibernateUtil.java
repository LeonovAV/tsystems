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
			System.err.println("Can not initialize sessionFactory for Hibernate" + e);
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
	public static Session beginTransaction() throws HibernateException {
		Session hibernateSession = HibernateUtil.getSession();
		hibernateSession.beginTransaction();
		return hibernateSession;
	}
	 
	/**
	 * 
	 */
	public static void commitTransaction() {
		HibernateUtil.getSession().getTransaction().commit();
	}
	
	/**
	 * 
	 */
	public static void rollbackTransaction() {
		HibernateUtil.getSession().getTransaction().rollback();
	}
	 
	/**
	 * 
	 */
	public static void closeSession() {
		HibernateUtil.getSession().close();
	}
	 
	/**
	 * 
	 * @return
	 */
	public static Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 
	 */
	public static void closeSessionFactory() {
		sessionFactory.close();
	}
	
	private static Configuration configureHibernate() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(Passenger.class);
		configuration.addAnnotatedClass(Ticket.class);
		configuration.addAnnotatedClass(Train.class);
		configuration.addAnnotatedClass(Station.class);
		configuration.addAnnotatedClass(Schedule.class);
		return configuration;
	}
}
