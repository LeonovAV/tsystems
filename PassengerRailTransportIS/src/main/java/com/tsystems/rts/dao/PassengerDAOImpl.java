package com.tsystems.rts.dao;

import java.util.Calendar;
import java.util.Date;

import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.utils.HibernateUtil;

public class PassengerDAOImpl extends GenericDAOImpl<Passenger, Long> implements PassengerDAO {
	
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
