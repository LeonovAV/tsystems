package com.tsystems.rts.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tsystems.rts.dao.StationDAO;
import com.tsystems.rts.dao.StationDAOImpl;
import com.tsystems.rts.entities.Station;
import com.tsystems.rts.utils.ServiceException;
import com.tsystems.rts.utils.DAOException;
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
	public void addNewStations(List<String> stations) throws ServiceException {
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
			throw new ServiceException("Transaction failed, because of lost connection", e);
		}
		catch (DAOException e) {
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
	 * Delete stations and update all routes and schedules, that contain chosen stations.
	 * @param stationIds list of unique station identifiers
	 */
	public void deleteStations(List<Long> stationIds) throws ServiceException {
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
		catch (DAOException e) {
			Logger.getLogger("LOG-FILE-APPENDER").fatal("Begin or commit transaction failed", e);
			try {
				HibernateUtil.rollbackTransaction();
			} catch (DAOException e2) {
				throw new ServiceException(e2);
			}
			throw new ServiceException(e);
		}
	}
	
	public List<Station> getAllStations() throws ServiceException {
		StationDAO sDao = new StationDAOImpl();
		List<Station> stations = null;
		try {
			HibernateUtil.beginTransaction();
			stations = sDao.findAllObjects(Station.class);
			HibernateUtil.commitTransaction();
		}
		catch (DAOException e) {
			Logger.getLogger("LOG-FILE-APPENDER").fatal("Begin or commit transaction failed", e);
			try {
				HibernateUtil.rollbackTransaction();
			} catch (DAOException e2) {
				throw new ServiceException(e2);
			}
			throw new ServiceException(e);
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
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (ExceptionInInitializerError e) {
			System.out.println(e.getMessage());
		}
	}
	
}
