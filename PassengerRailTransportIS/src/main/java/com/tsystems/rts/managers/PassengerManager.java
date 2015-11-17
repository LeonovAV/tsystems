package com.tsystems.rts.managers;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import com.tsystems.rts.dao.PassengerDAO;
import com.tsystems.rts.dao.PassengerDAOImpl;
import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class PassengerManager {
	
	private PassengerDAO pDao = new PassengerDAOImpl();
	
	public void saveNewPassenger(Passenger passenger) {
		try {
			HibernateUtil.beginTransaction();
			pDao.save(passenger);
			HibernateUtil.commitTransaction();
		}
		catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
		}
	}
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public Passenger findPassenger(String firstName, String lastName) {
		Passenger passenger = null;
		try {
			HibernateUtil.beginTransaction();
			passenger = pDao.findPassenger(firstName, lastName);
			HibernateUtil.commitTransaction();
		} catch (NonUniqueResultException e) {
		}
		catch (HibernateException e) {
		}
		return passenger;
	}
	
}
