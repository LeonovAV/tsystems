package com.tsystems.rts.dao;

import java.util.Date;
import java.util.List;

import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.DAOException;

/**
 * 
 * @author Anton
 *
 */
public interface TrainDAO extends GenericDAO<Train, Long> {
	
	/**
	 * Find trains between two stations based on a certain date between
	 * stations A (from) and B (to).
	 * @param firstStationId first station in the route (from)
	 * @param lastStationId last station in the route (to)
	 * @param departureTime date to start a train search
	 * @return a list of available trains
	 */
	List<Train> getTrainsBetweenStations(long firstStationId, long lastStationId, Date departureTime) throws DAOException;
	
}
