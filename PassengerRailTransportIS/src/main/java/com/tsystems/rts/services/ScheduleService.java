package com.tsystems.rts.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.tsystems.rts.dao.ScheduleDAO;
import com.tsystems.rts.dao.ScheduleDAOImpl;
import com.tsystems.rts.dao.StationDAO;
import com.tsystems.rts.dao.StationDAOImpl;
import com.tsystems.rts.dao.TrainDAO;
import com.tsystems.rts.dao.TrainDAOImpl;
import com.tsystems.rts.entities.Schedule;
import com.tsystems.rts.entities.Station;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class ScheduleService {
	
	/**
	 * Add new schedule for the train with station list, arrival and departure date
	 * for each station
	 * @param trainId unique identifier for the train
	 * @param stationIds list of station identifiers
	 * @param arrivalTimes list of arrival dates and times
	 * @param departureTimes list of departure dates and times
	 */
	public void addNewSchedule(long trainId, List<Long> stationIds, 
			List<Timestamp> arrivalTimes, List<Timestamp> departureTimes) {
		TrainDAO trainDao = new TrainDAOImpl();
		StationDAO sDao = new StationDAOImpl();
		try {
			HibernateUtil.beginTransaction();
			// Get train
			Train train = trainDao.findById(Train.class, trainId);
			
			// Create route and schedule for train
			List<Station> route = new ArrayList<Station>();
			List<Schedule> schedules = new ArrayList<Schedule>();
			
			Station station = null;
			Schedule schedule = null;
			for (int i = 0; i < stationIds.size(); ++i) {
				// Station to add is already exist
				station = sDao.findById(Station.class, stationIds.get(i));
				
				// Create new schedule
				schedule = new Schedule();
				schedule.setArrivalTime(arrivalTimes.get(i));
				schedule.setDepartureTime(departureTimes.get(i));
				schedule.setTrain(train);
				schedule.setStation(station);
				
				schedules.add(schedule);
				route.add(station);
			}
			
			train.setRoute(route);
			train.setSchedules(schedules);
			
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
		}
	}
	
	/**
	 * View schedule for a certain station on a certain date
	 * @param stationId unique identifier for the station 
	 * @param date chosen by client date
	 */
	public List<Schedule> viewSchedule(long stationId, Date date) {
		ScheduleDAO sDao = new ScheduleDAOImpl();
		List<Schedule> schedules = null;
		try {
			HibernateUtil.beginTransaction();
			schedules = sDao.getAllSchedules(stationId, date);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
		}
		return schedules;
	}
	
	public static void main(String[] args) {
		
	}
	
}
