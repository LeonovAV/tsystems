package com.tsystems.rts.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.HibernateException;

import com.tsystems.rts.dao.PassengerDAO;
import com.tsystems.rts.dao.PassengerDAOImpl;
import com.tsystems.rts.dao.TrainDAO;
import com.tsystems.rts.dao.TrainDAOImpl;
import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class TrainService {
	
	public void findTrains(long firstStationId, long lastStationId, Date departureDate) {
//		TrainDAO trainDao = new TrainDAOImpl();
//		// Check train date
//		Train train = trainDao.findById(Train.class, trainId);
//		int period = train.getPeriod();
//		long startingDate = train.getStartingDate().getTime();
//		long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - startingDate);
//		
//		if (days % period != 0) {
//			System.out.println("Can not buy ticket, because there is no train for this date");
//			HibernateUtil.rollbackTransaction();
//		}
	}
	
	/**
	 * 
	 * @param trainNo
	 * @param nSeats
	 * @param startingDate
	 * @param period
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
		}
	}
	
	/**
	 * 
	 * @return
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
		}
		return trains;
	}
	
	/**
	 * View all passengers for train without date constraint
	 * @param trainId
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
		}
		return passengers;
	}
	
	/**
	 * 
	 * @param trainId
	 * @param trainDepartureDate
	 * @return
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
		}
		return passengers;
	}
	
	public static void main(String[] args) {
		
	}
	
}
