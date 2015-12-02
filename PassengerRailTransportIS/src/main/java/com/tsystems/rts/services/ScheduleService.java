package com.tsystems.rts.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.DataException;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.tsystems.rts.dao.ScheduleDAO;
import com.tsystems.rts.dao.ScheduleDAOImpl;
import com.tsystems.rts.dao.StationDAO;
import com.tsystems.rts.dao.StationDAOImpl;
import com.tsystems.rts.dao.TrainDAO;
import com.tsystems.rts.dao.TrainDAOImpl;
import com.tsystems.rts.entities.Schedule;
import com.tsystems.rts.entities.Station;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.ServiceException;
import com.tsystems.rts.utils.DAOException;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public enum ScheduleService {
	
	INSTANCE;
	
	private ScheduleService() {
	}
	
	/**
	 * Allows one to create new route for the train or edit existed route.
	 * One can change arrival and departure time and add new station from
	 * existed to the end of the route. For the first station in the route
	 * arrival time must be NULL. For the last station in the route departure
	 * time must be NULL.  
	 * @param trainId train identifier, which route will be changed or created
	 * @param stationIds a list of identifiers of stations, which belong to the route
	 * @param arrivalTimes a list of train arrival times
	 * @param departureTimes a list of train departure times
	 * @throws ServiceException 
	 */
	public void saveSchedule(long trainId, List<Long> stationIds, 
			List<Timestamp> arrivalTimes, List<Timestamp> departureTimes) throws ServiceException {
		TrainDAO trainDao = new TrainDAOImpl();
		StationDAO sDao = new StationDAOImpl();
		try {
			HibernateUtil.beginTransaction();
			
			// Get train
			Train train = trainDao.findById(Train.class, trainId);
			
			// Obtain route and schedule for train
			List<Station> route = train.getRoute();
			List<Schedule> schedules = train.getSchedules();
			
			// Current number of stations
			int routeSize = route.size();
			// If add some new station to the route
			int newRouteSize = stationIds.size();
			
			Schedule schedule = null;
			// Update schedules for existed stations
			for (int i = 0; i < routeSize; ++i) {
				schedule = schedules.get(i);
				schedule.setArrivalTime(arrivalTimes.get(i));
				schedule.setDepartureTime(departureTimes.get(i));
			}
			
			// Add new stations and schedules for stations
			Station station = null;
			for (int i = routeSize; i < newRouteSize; ++i) {
				
				// Station to add is already exist
				station = sDao.findById(Station.class, stationIds.get(i));
				
				// Create schedule for new station
				schedule = new Schedule();
				schedule.setTrain(train);
				schedule.setStation(station);
				schedule.setArrivalTime(arrivalTimes.get(i));
				schedule.setDepartureTime(departureTimes.get(i));
				
				// Update train
				route.add(station);
				schedules.add(schedule);
			}
			
			HibernateUtil.commitTransaction();
		} catch (DAOException e) {
			Logger.getLogger("LOG-FILE-APPENDER").fatal("Begin or commit transaction failed", e);
			try {
				HibernateUtil.rollbackTransaction();
			} catch (DAOException e2) {
				throw new ServiceException(e2);
			}
			throw new ServiceException(e);
		}
	}
	
	/**
	 * View schedule for a certain station on a certain date
	 * @param stationId unique identifier for the station 
	 * @param date chosen by client date
	 * @throws ServiceException 
	 */
	public List<Schedule> viewSchedule(long stationId, Date date) throws ServiceException {
		ScheduleDAO sDao = new ScheduleDAOImpl();
		List<Schedule> schedules = null;
		try {
			HibernateUtil.beginTransaction();
			schedules = sDao.getAllSchedules(stationId, date);
			HibernateUtil.commitTransaction();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return schedules;
	}
	
	public static void main(String[] args) {
		try {
//		List<Long> stationIds = new ArrayList<Long>();
//		stationIds.add(1L);
//		stationIds.add(4L);
//		stationIds.add(5L);
//		// Add new station from existed to route
//		stationIds.add(7L);
//		
//		List<Timestamp> arrivalTimes = new ArrayList<Timestamp>();
//		arrivalTimes.add(null);
//		arrivalTimes.add(Timestamp.valueOf("2015-11-15 08:30:00"));
//		arrivalTimes.add(Timestamp.valueOf("2015-11-15 13:45:00"));
//		arrivalTimes.add(Timestamp.valueOf("2015-11-15 18:15:00"));
//		List<Timestamp> departureTimes = new ArrayList<Timestamp>();
//		departureTimes.add(Timestamp.valueOf("2015-11-14 17:10:00"));
//		departureTimes.add(Timestamp.valueOf("2015-11-15 08:37:00"));
//		departureTimes.add(Timestamp.valueOf("2015-11-15 13:59:00"));
//		departureTimes.add(null);
		
		
			//ScheduleService.INSTANCE.saveSchedule(2L, stationIds, arrivalTimes, departureTimes);
			Calendar cal = Calendar.getInstance();
			cal.set(2015, Calendar.NOVEMBER, 30);
			Date date = cal.getTime();
			cal.clear();
			
			List<Schedule> schedules = ScheduleService.INSTANCE.viewSchedule(1L, date);
			for (Schedule schedule : schedules) {
				System.out.println(schedule.getTrain().getTrainId());
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
}
