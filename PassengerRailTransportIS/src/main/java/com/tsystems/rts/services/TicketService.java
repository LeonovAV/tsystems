package com.tsystems.rts.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import com.tsystems.rts.dao.PassengerDAO;
import com.tsystems.rts.dao.PassengerDAOImpl;
import com.tsystems.rts.dao.TicketDAO;
import com.tsystems.rts.dao.TicketDAOImpl;
import com.tsystems.rts.dao.TrainDAO;
import com.tsystems.rts.dao.TrainDAOImpl;
import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.entities.Ticket;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.Constants;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class TicketService {
	
	public void purchaseTicket(long trainId, Timestamp trainDepartureDate, String firstName, 
			String lastName, Date birthdate) {
		// Initialize DAOs
		PassengerDAO pDao = new PassengerDAOImpl();
		TicketDAO tDao = new TicketDAOImpl();
		TrainDAO trainDao = new TrainDAOImpl();
		try {
			HibernateUtil.beginTransaction();
			Passenger passenger = pDao.findPassenger(firstName, lastName, birthdate);
			// Check if passenger has not already purchased a ticket for a train
			if (passenger != null) {
				if (pDao.hasTicketForTrain(passenger.getPassengerId(), trainId, trainDepartureDate)) {
					System.out.println("Passenger has a ticket to this train");
					HibernateUtil.rollbackTransaction();
					return;
				}
			}
			
			// Number of tickets for current train on a certain date
			int nPurchasedTickets = tDao.getPurchasedTicketsForTrain(trainId, trainDepartureDate).size();
			// Get seats number for the train
			Train train = trainDao.findById(Train.class, trainId);
			int nSeats = train.getSeatsNumber();
			
			// No free seats in the train
			if (nPurchasedTickets > nSeats) {
				System.out.println("No free seats in the train");
				HibernateUtil.rollbackTransaction();
				return;
			}
			
			// Time constraint
			long currentTime = System.currentTimeMillis();
			long remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(trainDepartureDate.getTime() - currentTime) + 1; 
			
			// Remaining time before the train departure should be more than 10 minutes
			if (remainingMinutes <= Constants.MAX_REMAINING_TIME) {
				System.out.println("Can not by ticket because of time limit. Remaining minutes: " + remainingMinutes);
				HibernateUtil.rollbackTransaction();
				return;
			}
			
			// Ticket can be purchased by a passenger
			// Passenger has never bought tickets in the system
			if (passenger == null) {
				passenger = new Passenger();
				passenger.setFirstName(firstName);
				passenger.setLastName(lastName);
				passenger.setBirthdate(birthdate);
				pDao.save(passenger);
			}
			
			// Save ticket
			Ticket newTicket = new Ticket();
			newTicket.setPassenger(passenger);
			newTicket.setTrain(train);
			newTicket.setTrainDepartureDate(trainDepartureDate);
			tDao.save(newTicket);
			
			HibernateUtil.commitTransaction();
		}
		catch (NonUniqueResultException e) {
		}
		catch (HibernateException e) {
		}
	}
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(1992, Calendar.JANUARY, 9);
		Date date = cal.getTime();
		cal.clear();
		new TicketService().purchaseTicket(2, Timestamp.valueOf("2015-11-18 23:18:00"), 
				"Anton", "Leonov", date);
	}
}
