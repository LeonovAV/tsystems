package com.tsystems.rts.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tsystems.rts.dao.StationDAO;
import com.tsystems.rts.dao.StationDAOImpl;
import com.tsystems.rts.entities.Station;
import com.tsystems.rts.utils.BusinessLogicException;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public enum StationService {
	
	INSTANCE;
	
	private StationService() {
	}
	
	/**
	 * Add several stations. Station name should be unique
	 * @param stations list of station names
	 */
	public void addNewStations(List<String> stations) throws BusinessLogicException {
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
			// Log exception
			throw new BusinessLogicException(e);
		} catch (MySQLIntegrityConstraintViolationException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			throw new BusinessLogicException("Can not save the same object in database", e);
		}
	}
	
	/**
	 * Delete stations and update all routes and schedules, that contain chosen stations.
	 * @param stationIds list of unique station identifiers
	 */
	public void deleteStations(List<Long> stationIds) throws BusinessLogicException {
		StationDAO sDao = new StationDAOImpl();
		try {
			Station station = null;
			HibernateUtil.beginTransaction();
			for (Long stationId : stationIds) {
				station = sDao.findById(Station.class, stationId);
				if (station != null) {
					sDao.delete(station);
				}
			}
			HibernateUtil.commitTransaction();
		}
		catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			throw new BusinessLogicException(e);
		}
	}
	
	public List<Station> getAllStations() throws BusinessLogicException {
		StationDAO sDao = new StationDAOImpl();
		List<Station> stations = null;
		try {
			HibernateUtil.beginTransaction();
			stations = sDao.findAllObjects(Station.class);
			HibernateUtil.commitTransaction();
		}
		catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			// Log exception
			throw new BusinessLogicException(e);
		}
		return stations;
	}
	
	public static void main(String[] args) {
		List<String> stations = new ArrayList<String>();
		stations.add("Gll");
		//stations.add(7L);
//		
//		StationService.INSTANCE.deleteStations(stations);
		try {
			StationService.INSTANCE.addNewStations(stations);
		} catch (BusinessLogicException e) {
			e.printStackTrace();
		}
	}
	
}
