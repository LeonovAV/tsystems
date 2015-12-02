package com.tsystems.rts.dao;

import java.util.Date;
import java.util.List;

import com.tsystems.rts.entities.Schedule;
import com.tsystems.rts.utils.DAOException;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public interface ScheduleDAO extends GenericDAO<Schedule, Long>{
	
	/**
	 * Allows to obtain all schedules (train arrival and departure date) for 
	 * the station on a certain date
	 * @param stationId identifier for the chosen station
	 * @param date chosen date
	 * @return list of available trains and their arrival and departure time
	 */
	List<Schedule> getAllSchedules(long stationId, Date date) throws DAOException;
	
}
