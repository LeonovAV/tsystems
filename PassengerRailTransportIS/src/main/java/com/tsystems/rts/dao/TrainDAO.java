package com.tsystems.rts.dao;

import java.util.Date;
import java.util.List;

import com.tsystems.rts.entities.Train;

/**
 * 
 * @author Anton
 *
 */
public interface TrainDAO extends GenericDAO<Train, Long> {
	
	/**
	 * Find all trains between two stations A and B, that could be chosen by client
	 * @param firstStationId initial station (from)
	 * @param lastStationId last station (to)
	 * @param departureDate when client wants to start trip from initial station
	 * @return list of available trains
	 */
	List<Train> getTrainsBetweenStations(long firstStationId, long lastStationId, Date departureDate);
	
}
