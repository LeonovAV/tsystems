package com.tsystems.rts.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.utils.DAOException;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public interface PassengerDAO extends GenericDAO<Passenger, Long> {
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public Passenger findPassenger(String firstName, String lastName, Date birthdate) throws DAOException;
	
	/**
	 * 
	 * @param passengerId
	 * @param trainNo
	 * @param trainDepartureDate
	 * @return
	 */
	public boolean hasTicketForTrain(long passengerId, long trainId, Timestamp trainDepartureDate) throws DAOException;
	
	/**
	 * 
	 * @param trainId
	 * @return
	 */
	List<Passenger> getRegisteredPassengers(long trainId) throws DAOException;
	
	/**
	 * 
	 * @param trainId
	 * @param trainDepartureDate
	 * @return
	 */
	List<Passenger> getRegisteredPassengers(long trainId, Timestamp trainDepartureDate) throws DAOException;
}
