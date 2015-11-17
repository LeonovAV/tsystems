package com.tsystems.rts.dao;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.Query;

import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 *
 */
public class PassengerDAOImpl extends GenericDAOImpl<Passenger, Long> implements PassengerDAO {
	
	public Passenger findPassenger(String firstName, String lastName) {
		String sqlQuery = "SELECT p FROM Passenger p WHERE p.firstName = :firstName AND p.lastName = :lastName";
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("firstName", firstName).setParameter("lastName", lastName);
		return findObject(query);
	}
	
	public static void main(String[] args) {
		HibernateUtil.beginTransaction();
		PassengerDAO pDao = new PassengerDAOImpl();
		Passenger passenger = pDao.findById(Passenger.class, 1L);
		if (passenger != null)
			System.out.println(passenger.getPassengerId() + " " + passenger.getFirstName() 
			+ " "+ passenger.getLastName());
		Passenger newPassenger = new Passenger();
		newPassenger.setFirstName("Petay");
		newPassenger.setLastName("Petrov");
		Calendar cal = Calendar.getInstance();
		cal.set(1992, Calendar.JANUARY, 9);
		Date date = cal.getTime();
		cal.clear();
		newPassenger.setBirthdate(date);
		long newId = pDao.save(newPassenger);
		System.out.println("New id: " + newId);
		pDao.delete(newPassenger);
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
	}
	
}
