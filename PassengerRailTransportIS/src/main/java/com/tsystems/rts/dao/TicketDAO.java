package com.tsystems.rts.dao;

import java.sql.Timestamp;
import java.util.List;

import com.tsystems.rts.entities.Ticket;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public interface TicketDAO extends GenericDAO<Ticket, Long> {
	
	public List<Ticket> getPurchasedTicketsForTrain(long trainId, Timestamp trainDepartureDate);
	
}
