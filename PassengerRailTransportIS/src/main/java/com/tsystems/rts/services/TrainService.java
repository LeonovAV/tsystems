package com.tsystems.rts.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tsystems.rts.dao.PassengerDAO;
import com.tsystems.rts.dao.PassengerDAOImpl;
import com.tsystems.rts.dao.TrainDAO;
import com.tsystems.rts.dao.TrainDAOImpl;
import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * Class provides functions, which are responsible for searching trains,
 * adding new trains, delete chosen train, viewing all trains and passengers
 * for a certain train. Class is implemented as singleton. 
 * @author Anton
 * @version 0.0.1
 *
 */
public enum TrainService {
	
	INSTANCE;
	
	private TrainService() {
	}
	
	/**
	 * Find trains between station A (from) and B (to) on a certain departure time 
	 * (for the first station) 
	 * @param firstStationId
	 * @param lastStationId
	 * @param departureTime
	 * @return
	 */
	public List<Train> findTrains(long firstStationId, long lastStationId, Date departureTime) {
		TrainDAO trainDao = new TrainDAOImpl();
		List<Train> trains = null;
		try {
			HibernateUtil.beginTransaction();
			trains = trainDao.getTrainsBetweenStations(firstStationId, lastStationId, departureTime);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			// Create and throw custom exception
		}
		return trains;
	}
	
	/**
	 * Add new train.
	 * @param trainNo unique train number
	 * @param nSeats number of seats in the train
	 * @param startingDate date, when train starts trips
	 * @param period difference between two consequent trips (next trip date is equals 
	 * to startingDate plus several times period)
	 */
	public void addNewTrain(long trainNo, int nSeats, Timestamp startingDate, int period) {
		TrainDAO trainDao = new TrainDAOImpl();
		Train train = new Train();
		train.setTrainNo(trainNo);
		train.setSeatsNumber(nSeats);
		train.setStartingDate(startingDate);
		train.setPeriod(period);
		try {
			HibernateUtil.beginTransaction();
			trainDao.save(train);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			// Create and throw custom exception
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			// Log exception
			// Create and throw custom exception
		}
	}
	
	/**
	 * Delete train with its route and schedule. Stations will not be deleted.
	 * All ticket for this train will be also deleted.
	 * @param trainId unique train identifier
	 */
	public void deleteTrain(long trainId) {
		TrainDAO trainDao = new TrainDAOImpl();
		try {
			HibernateUtil.beginTransaction();
			Train train = trainDao.findById(Train.class, trainId);
			if (train != null) {
				trainDao.delete(train);
			}
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			// Create and throw custom exception
		}
	}
	
	/**
	 * View all trains, which exist in the system.
	 * @return list of trains
	 */
	public List<Train> viewAllTrains() {
		TrainDAO trainDao = new TrainDAOImpl();
		List<Train> trains = null;
		try {
			HibernateUtil.beginTransaction();
			trains = trainDao.findAllObjects(Train.class);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			// Create and throw custom exception
		}
		return trains;
	}
	
	/**
	 * View all passengers for a certain train without date constraint
	 * @param trainId unique identifier for the train
	 * @return list of passengers
	 */
	public List<Passenger> viewPassengers(long trainId) {
		PassengerDAO pDao = new PassengerDAOImpl();
		List<Passenger> passengers = null;
		try {
			HibernateUtil.beginTransaction();
			passengers = pDao.getRegisteredPassengers(trainId);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			// Create and throw custom exception
		}
		return passengers;
	}
	
	/**
	 * View all passengers for a certain train on a certain date
	 * @param trainId unique identifier for the train
	 * @param trainDepartureDate chosen train departure date
	 * @return list of passengers
	 */
	public List<Passenger> viewPassengers(long trainId, Timestamp trainDepartureDate) {
		PassengerDAO pDao = new PassengerDAOImpl();
		List<Passenger> passengers = null;
		try {
			HibernateUtil.beginTransaction();
			passengers = pDao.getRegisteredPassengers(trainId, trainDepartureDate);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			// Create and throw custom exception
		}
		return passengers;
	}
	
	public static void main(String[] args) {
//		List<Train> trains = TrainService.INSTANCE.viewAllTrains();
//		
//		for (Train train : trains) {
//			System.out.println(train.getTrainNo());
//		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(2015, Calendar.NOVEMBER, 30);
		Date date = cal.getTime();
		cal.clear();
		
		List<Train> trains = TrainService.INSTANCE.findTrains(1L, 5L, date);
		
		for (Train train : trains) {
			System.out.println(train.getTrainNo());
		}
	}
	
}
