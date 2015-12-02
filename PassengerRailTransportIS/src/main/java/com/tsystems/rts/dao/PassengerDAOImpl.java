package com.tsystems.rts.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.utils.DAOException;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class PassengerDAOImpl extends GenericDAOImpl<Passenger, Long> implements PassengerDAO {
	
	public Passenger findPassenger(String firstName, String lastName, Date birthdate) throws DAOException {
		String sqlQuery = "SELECT p FROM Passenger p WHERE p.firstName = :firstName "
				+ "AND p.lastName = :lastName AND p.birthdate = :birthdate";
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("firstName", firstName).setParameter("lastName", lastName)
				.setParameter("birthdate", birthdate);
		return findObject(query);
	}
	
	public boolean hasTicketForTrain(long passengerId, long trainId, Timestamp trainDepartureDate) throws DAOException {
		String sqlQuery = "SELECT p FROM Passenger p JOIN p.purchasedTickets t WHERE "
				+ "t.passenger.passengerId = :passengerId  AND t.train.trainId = :trainId "
				+ "AND t.trainDepartureDate = :trainDepartureDate";
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("trainId", trainId)
				.setParameter("passengerId", passengerId)
				.setParameter("trainDepartureDate", trainDepartureDate);
		Passenger passenger = findObject(query);
		return passenger == null ? false : true;
	}
	
	public List<Passenger> getRegisteredPassengers(long trainId) throws DAOException {
		String sqlQuery = "SELECT p FROM Passenger p JOIN p.purchasedTickets t WHERE "
				+ "t.train.trainId = :trainId";
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("trainId", trainId);
		return findObjects(query);
	}

	public List<Passenger> getRegisteredPassengers(long trainId, Timestamp trainDepartureDate) throws DAOException {
		String sqlQuery = "SELECT p FROM Passenger p JOIN p.purchasedTickets t WHERE "
				+ "t.train.trainId = :trainId AND t.trainDepartureDate = :trainDepartureDate";
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("trainId", trainId)
				.setParameter("trainDepartureDate", trainDepartureDate);
		return findObjects(query);
	}
	
	public static void main(String[] args) {
		try {
			HibernateUtil.beginTransaction();
			PassengerDAO pDao = new PassengerDAOImpl();
			pDao.hasTicketForTrain(1, 2, Timestamp.valueOf("2015-11-14 17:10:00"));
			
			
//			Passenger passenger = pDao.findById(Passenger.class, 1L);
//			if (passenger != null)
//				System.out.println(passenger.getPassengerId() + " " + passenger.getFirstName() 
//				+ " "+ passenger.getLastName());
//			Passenger newPassenger = new Passenger();
//			newPassenger.setFirstName("Petay");
//			newPassenger.setLastName("Petrov");
//			Calendar cal = Calendar.getInstance();
//			cal.set(1992, Calendar.JANUARY, 9);
//			Date date = cal.getTime();
//			cal.clear();
//			newPassenger.setBirthdate(date);
//			long newId = pDao.save(newPassenger);
//			System.out.println("New id: " + newId);
//			pDao.delete(newPassenger);
			
//			List<Passenger> p = pDao.getRegisteredPassengers(2L);
//			
//			for (Passenger passenger : p) {
//				System.out.println(passenger.getPassengerId());
//			}
			
			List<Passenger> p = pDao.getRegisteredPassengers(2L, Timestamp.valueOf("2015-11-14 17:10:00"));
			
			for (Passenger passenger : p) {
				System.out.println(passenger.getPassengerId());
			}
		
			HibernateUtil.commitTransaction();
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}	
}
