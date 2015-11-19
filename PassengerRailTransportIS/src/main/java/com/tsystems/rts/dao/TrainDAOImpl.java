package com.tsystems.rts.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.HibernateUtil;

public class TrainDAOImpl extends GenericDAOImpl<Train, Long> implements TrainDAO {
	
	public List<Train> getTrainsBetweenStations(long firstStationId, long lastStationId, 
			Date departureDate) {
		List<Train> trains = getTrains(firstStationId, departureDate);
		
		String sql = "SELECT st.trains FROM Station st JOIN st.schedules sch "
				+ "WHERE st.stationId = :lastStationId AND sch.train IN (:items)";
		Query query = HibernateUtil.getSession().createQuery(sql)
				.setParameter("lastStationId", lastStationId)
				.setParameterList("items", trains);
		return findObjects(query);
	}
	
	/**
	 * Find all trains, which go through certain station on a certain date
	 * @param stationId
	 * @param departureDate
	 * @return list of available trains
	 */
	private List<Train> getTrains(long stationId, Date departureDate) {
		String sql = "SELECT st.trains FROM Station st JOIN st.schedules sch "
				+ "WHERE st.stationId = :stationId AND DATE(sch.departureTime) = :departureDate";
		Query query = HibernateUtil.getSession().createQuery(sql)
				.setParameter("stationId", stationId)
				.setParameter("departureDate", departureDate);
		return findObjects(query);
	}
	
	public static void main(String[] args) {
		HibernateUtil.beginTransaction();
		TrainDAO trainDao = new TrainDAOImpl();
		
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
