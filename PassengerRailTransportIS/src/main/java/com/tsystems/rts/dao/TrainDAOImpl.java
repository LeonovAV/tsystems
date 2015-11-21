package com.tsystems.rts.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class TrainDAOImpl extends GenericDAOImpl<Train, Long> implements TrainDAO {
	
	public List<Train> getTrainsBetweenStations(long firstStationId, long lastStationId, 
			Date departureTime) {
		List<Train> trains = getTrainsForStation(firstStationId);
		List<Train> trainsFromFirstStation = getTrainsBasedOnPeriod(trains, departureTime);
		
		String sql = "SELECT trns FROM Station st JOIN st.schedules schs JOIN "
				+ "st.trains trns WHERE st.stationId = :stationId AND trns IN (:ids)";
		Query query = HibernateUtil.getSession().createQuery(sql)
				.setParameter("stationId", lastStationId)
				.setParameterList("ids", trainsFromFirstStation);
		
		return findObjects(query);
	}
	
	/**
	 * Find all trains, which go through chosen station.
	 * @param stationId chosen station identifier
	 * @return a list of available trains
	 */
	private List<Train> getTrainsForStation(long stationId) {
		String sql = "SELECT trns FROM Station st JOIN st.schedules schs JOIN "
				+ "st.trains trns WHERE st.stationId = :stationId";
		Query query = HibernateUtil.getSession().createQuery(sql)
				.setParameter("stationId", stationId);
		return findObjects(query);
	}
	
	/**
	 * Find trains based on a list of trains, which go through first station (from),
	 * and chosen date. Period is included to identify train departure date. 
	 * @param trains list of trains
	 * @param departureTime chosen date to start trip
	 * @return a list of available trains
	 */
	private List<Train> getTrainsBasedOnPeriod(List<Train> trains, Date departureTime) {
		String sql = "SELECT t FROM Train t JOIN t.schedules schs WHERE t IN (:ids) "
				+ "AND DATEDIFF(:departureTime, schs.departureTime) % t.period = 0";
		Query query = HibernateUtil.getSession().createQuery(sql)
				.setParameterList("ids", trains)
				.setParameter("departureTime", departureTime);
		return findObjects(query);
	}
	
	public static void main(String[] args) {
		HibernateUtil.beginTransaction();
		TrainDAOImpl trainDao = new TrainDAOImpl();
		
		Calendar cal = Calendar.getInstance();
		cal.set(2015, Calendar.NOVEMBER, 14);
		Date date = cal.getTime();
		cal.clear();
		
		System.out.println(trainDao.getTrainsBetweenStations(1L, 4L, date).get(0).getTrainNo());
		
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
	}
	
}
