package com.tsystems.rts.services;

/**
 * ServiceLocator provides access to all services in the system
 * @author Anton
 * @version 0.0.1
 *
 */
public enum ServiceLocator {
	
	/**
	 * Unique instance of Service Locator
	 */
	INSTANCE;
	
	private ServiceLocator() {
	}
	
	/**
	 * 
	 * @return
	 */
	public TrainService getTrainService() {
		return new TrainService();
	}
	
	/**
	 * 
	 * @return
	 */
	public TicketService getTicketService() {
		return new TicketService();
	}
	
	/**
	 * 
	 * @return
	 */
	public ScheduleService getScheduleService() {
		return new ScheduleService();
	}
	
	/**
	 * 
	 * @return
	 */
	public StationService getStationService() {
		return new StationService();
	}
	
}
