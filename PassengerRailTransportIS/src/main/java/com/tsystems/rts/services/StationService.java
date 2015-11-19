package com.tsystems.rts.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import com.tsystems.rts.dao.StationDAO;
import com.tsystems.rts.dao.StationDAOImpl;
import com.tsystems.rts.entities.Station;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class StationService {
	
	/**
	 * Add several stations. Station name should be unique
	 * @param stations list of station names
	 */
	public void addNewStations(List<String> stations) {
		// Initialize DAOs
		StationDAO sDao = new StationDAOImpl();
		
		try {
			Station station = null;
			HibernateUtil.beginTransaction();
			for (String stationName : stations) {
				// Create new station and save it
				station = new Station();
				station.setName(stationName);
				sDao.save(station);
			}
			HibernateUtil.commitTransaction();
		}
		catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
		}
	}
	
	/**
	 * Delete stations and update all routes and schedules, that contain chosen stations.
	 * @param stationIds list of unique station identifiers
	 */
	public void deleteStations(List<Long> stationIds) {
		StationDAO sDao = new StationDAOImpl();
		try {
			Station station = null;
			HibernateUtil.beginTransaction();
			for (Long stationId : stationIds) {
				station = sDao.findById(Station.class, stationId);
				if (station != null) {
					sDao.delete(station);
				}
				else {
					// Skip station and do not delete it
					System.out.println("Can not find station");
				}
			}
			HibernateUtil.commitTransaction();
		}
		catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
		}
	}
	
	public static void main(String[] args) {
		List<Long> stations = new ArrayList<Long>();
		stations.add(6L);
		stations.add(7L);
		
		new StationService().deleteStations(stations);
	}
	
}
