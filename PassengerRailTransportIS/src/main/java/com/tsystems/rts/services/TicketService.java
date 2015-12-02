package com.tsystems.rts.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tsystems.rts.dao.PassengerDAO;
import com.tsystems.rts.dao.PassengerDAOImpl;
import com.tsystems.rts.dao.TicketDAO;
import com.tsystems.rts.dao.TicketDAOImpl;
import com.tsystems.rts.dao.TrainDAO;
import com.tsystems.rts.dao.TrainDAOImpl;
import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.entities.Ticket;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.ServiceException;
import com.tsystems.rts.utils.Constants;
import com.tsystems.rts.utils.DAOException;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public enum TicketService {
	
	INSTANCE;
	
	private TicketService() {
	}
	
	/**
	 * Allows to purchase tickets (forward and back) to a certain train on a certain date. If back or forward 
	 * ticket can not be purchased, transaction is stopped. 
	 * @param trainIdForward unique forward train identifier
	 * @param trainForwardDepartureDate
	 * @param trainIdBack
	 * @param trainBackDepartureDate
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @throws ServiceException
	 */
	public String purchaseTicket(long trainIdForward, Timestamp trainForwardDepartureDate, 
			long trainIdBack, Timestamp trainBackDepartureDate, String firstName, 
			String lastName, Date birthdate) throws ServiceException {
		// Initialize DAOs
		PassengerDAO pDao = new PassengerDAOImpl();
		TicketDAO tDao = new TicketDAOImpl();
		TrainDAO trainDao = new TrainDAOImpl();
		
		try {
			HibernateUtil.beginTransaction();
			
			Passenger passenger = pDao.findPassenger(firstName, lastName, birthdate);
			
			// Ticket can be purchased by a passenger
			// Passenger has never bought tickets in the system
			if (passenger == null) {
				passenger = new Passenger();
				passenger.setFirstName(firstName);
				passenger.setLastName(lastName);
				passenger.setBirthdate(birthdate);
				pDao.save(passenger);
			}
			
			String errorMsg = this.checkConditions(pDao, passenger, trainDao, trainIdForward, trainForwardDepartureDate, tDao);
			if (!errorMsg.isEmpty()) {
				return errorMsg;
			}
			this.saveTicket(tDao, passenger, trainDao, trainIdForward, trainForwardDepartureDate);
			
			if (trainIdBack != 0 && trainBackDepartureDate != null) {
				errorMsg = this.checkConditions(pDao, passenger, trainDao, trainIdBack, trainBackDepartureDate, tDao);
				if (!errorMsg.isEmpty()) {
					return errorMsg;
				}
				this.saveTicket(tDao, passenger, trainDao, trainIdBack, trainBackDepartureDate);
			}
			HibernateUtil.commitTransaction();
			
			return "";
		}
		catch (DAOException e) {
			Logger.getLogger("LOG-FILE-APPENDER").fatal("Begin or commit transaction failed", e);
			try {
				HibernateUtil.rollbackTransaction();
			} catch (DAOException e2) {
				throw new ServiceException(e2);
			}
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Check if passenger has already purchased a ticket to the train or not.
	 * If yes, he can not buy one more ticket to this train.
	 * @param pDao
	 * @param passenger
	 * @param trainId
	 * @param trainDepartureDate
	 * @throws ServiceException
	 */
	private String checkPassenger(PassengerDAO pDao, Passenger passenger, long trainId, Timestamp trainDepartureDate) throws DAOException {
		if (passenger != null) {
			if (pDao.hasTicketForTrain(passenger.getPassengerId(), trainId, trainDepartureDate)) {
				HibernateUtil.rollbackTransaction();
				Logger.getLogger("LOG-FILE-APPENDER").info(passenger + " has already purchased a ticket for the train " + trainId);
				return "Passenger has already purchased a ticket for the train";
			}
		}
		return "";
	}
	
	private Train findTrain(TrainDAO trainDao, long trainId) throws DAOException {
		Train train = trainDao.findById(Train.class, trainId);
		return train;
	}
	
	/**
	 * Check if there are free seats in the train. Passenger can not buy ticket, if no free seats are in the train.
	 * @param tDao
	 * @param trainDao
	 * @param trainId
	 * @param trainDepartureDate
	 * @throws ServiceException
	 */
	private String checkFreeSeats(TicketDAO tDao, Train train, Timestamp trainDepartureDate) throws DAOException {
		// Number of tickets for current train on a certain date
		int nPurchasedTickets = 0;
		List<Ticket> tickets = tDao.getPurchasedTicketsForTrain(train.getTrainId(), trainDepartureDate);
		if (tickets != null) {
			nPurchasedTickets = tickets.size();
		}
		
		int nSeats = train.getSeatsNumber();
		
		// No free seats in the train
		if (nPurchasedTickets > nSeats) {
			HibernateUtil.rollbackTransaction();
			Logger.getLogger("LOG-FILE-APPENDER").info(train + " has no free seats");
			return "Train " + train.getTrainNo() + " has no free seats";
		}
		return "";
	}
	
	/**
	 * Check if the remaining time before train departure is less then 10 minutes, passenger can buy a ticket,
	 * otherwise not.
	 * @param trainDepartureDate
	 * @throws ServiceException
	 */
	private String checkTimeConstraint(Timestamp trainDepartureDate) throws DAOException {
		// Time constraint
		long currentTime = System.currentTimeMillis();
		long remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(trainDepartureDate.getTime() - currentTime) + 1; 
		
		// Remaining time before the train departure should be more than 10 minutes
		if (remainingMinutes <= Constants.MAX_REMAINING_TIME) {
			HibernateUtil.rollbackTransaction();
			Logger.getLogger("LOG-FILE-APPENDER").info("Can not by ticket because of the time limit. "
					+ "Remaining minutes is less then 10 before train departure.");
			return "Can not by ticket because of the time limit. Remaining minutes is less then 10 before train departure.";
		}
		return "";
	}
	
	/**
	 * If all business logic conditions ({@link #checkPassenger(PassengerDAO, Passenger, long, Timestamp)},
	 * {@link #checkFreeSeats(TicketDAO, TrainDAO, long, Timestamp)}, {@link #checkTimeConstraint(Timestamp)}) 
	 * are satisfied, then ticket can be purchased and saved in database.
	 * @param tDao
	 * @param passenger
	 * @param train
	 * @param trainDepartureDate
	 * @return
	 * @throws MySQLIntegrityConstraintViolationException
	 */
	private void saveTicket(TicketDAO tDao, Passenger passenger, TrainDAO trainDao, long trainId, Timestamp trainDepartureDate) throws DAOException {
		// Save ticket
		Ticket newTicket = new Ticket();
		newTicket.setPassenger(passenger);
		Train train = this.findTrain(trainDao, trainId);
		newTicket.setTrain(train);
		newTicket.setTrainDepartureDate(trainDepartureDate);
		tDao.save(newTicket);
		Logger.getLogger("LOG-FILE-APPENDER").info(passenger + " has bought a " + newTicket + " to the " + train);
	}
	
	private String checkConditions(PassengerDAO pDao, Passenger passenger, TrainDAO trainDao, long trainId, Timestamp trainDepartureDate, TicketDAO tDao) throws DAOException {
		String errorMsg = this.checkPassenger(pDao, passenger, trainId, trainDepartureDate);
		if (!errorMsg.isEmpty()) {
			return errorMsg;
		}
		
		Train train = this.findTrain(trainDao, trainId);
		errorMsg = "Can not find train";
		if (train == null) {
			return errorMsg;
		}
		
		errorMsg = this.checkFreeSeats(tDao, train, trainDepartureDate);
		if (!errorMsg.isEmpty()) {
			return errorMsg;
		}
		
		errorMsg = this.checkTimeConstraint(trainDepartureDate);
		if (!errorMsg.isEmpty()) {
			return errorMsg;
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(1992, Calendar.JANUARY, 9);
		Date date = cal.getTime();
		cal.clear();
		
		try {
			
			String msg = TicketService.INSTANCE.purchaseTicket(6L, Timestamp.valueOf("2015-12-02 19:10:00"), 
					0L, null,
					"Anton", "Leonov", date);
			
			System.out.println(msg.isEmpty() ? "OK" : msg);
			
//			TicketService.INSTANCE.purchaseTicket(5L, Timestamp.valueOf("2015-12-02 15:10:00"), 
//					7L, Timestamp.valueOf("2015-12-03 17:10:00"),
//					"Anton", "Leonov", date);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
