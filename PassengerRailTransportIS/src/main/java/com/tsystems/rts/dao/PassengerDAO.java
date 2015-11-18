package com.tsystems.rts.dao;

import java.sql.Timestamp;
import java.util.Date;

import com.tsystems.rts.entities.Passenger;

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
	public Passenger findPassenger(String firstName, String lastName, Date birthdate);
	
	/**
	 * 
	 * @param passengerId
	 * @param trainNo
	 * @param trainDepartureDate
	 * @return
	 */
	public boolean hasTicketForTrain(long passengerId, long trainId, Timestamp trainDepartureDate);
}
