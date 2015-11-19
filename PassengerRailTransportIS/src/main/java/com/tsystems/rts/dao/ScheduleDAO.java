package com.tsystems.rts.dao;

import java.util.Date;
import java.util.List;

import com.tsystems.rts.entities.Schedule;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public interface ScheduleDAO extends GenericDAO<Schedule, Long>{
	/**
	 * 
	 * @param stationId
	 * @param date
	 * @return
	 */
	List<Schedule> getAllSchedules(long stationId, Date date);
}
