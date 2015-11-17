package com.tsystems.rts.services;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class ServiceLocator {
	
	/**
	 * 
	 * @return
	 */
	public static TrainService getTrainService() {
		return new TrainService();
	}
	
	/**
	 * 
	 * @return
	 */
	public static TicketService getTicketService() {
		return new TicketService();
	}
	
}
