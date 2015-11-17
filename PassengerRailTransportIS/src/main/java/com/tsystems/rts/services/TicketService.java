package com.tsystems.rts.services;

import java.sql.Timestamp;
import java.util.Date;

import com.tsystems.rts.entities.Passenger;
import com.tsystems.rts.managers.PassengerManager;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class TicketService {
	
	public void purchaseTicket(long trainNo, Timestamp trainArrivalDate, String firstName, 
			String lastName, Date birthdate) {
		PassengerManager pManager = new PassengerManager();
		Passenger passenger = pManager.findPassenger(firstName, lastName);
		if (passenger == null) {
			pManager.saveNewPassenger(passenger);
		}
		else {
			// Check ticket for current train
		}
		
		System.out.println(passenger.getPassengerId() + " " + passenger.getFirstName());
	}
	
	public static void main(String[] args) {	
		new TicketService().purchaseTicket(21, null, "Anton", "Leonov", null);
	}
}
