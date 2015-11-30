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
		String sql = "SELECT train.train_id, train.train_no, train.seats_number, train.starting_date, train.period "
				+ "FROM station JOIN schedule ON station.station_id = schedule.station_id JOIN train "
				+ "ON schedule.train_id = train.train_id WHERE station.station_id = :lastStationId AND "
				+ "train.train_id IN ((SELECT train_id FROM(SELECT train_id, "
				+ "(DATEDIFF(:departureTime, departure_time)) % period module FROM(SELECT train.train_id, "
				+ "schedule.departure_time, train.period FROM station JOIN schedule ON "
				+ "station.station_id = schedule.station_id JOIN train ON schedule.train_id = train.train_id "
				+ "WHERE station.station_id = :firstStationId) as temp) as temp2 WHERE module = 0))";
		
		Query query = HibernateUtil.getSession().createSQLQuery(sql)
				.addEntity(Train.class)
				.setParameter("lastStationId", lastStationId)
				.setParameter("departureTime", departureTime)
				.setParameter("firstStationId", firstStationId);
		
		return findObjects(query);
	}
	
	public static void main(String[] args) {
		HibernateUtil.beginTransaction();
		TrainDAOImpl trainDao = new TrainDAOImpl();
		
		Calendar cal = Calendar.getInstance();
		cal.set(2015, Calendar.NOVEMBER, 30);
		Date date = cal.getTime();
		cal.clear();
		
		List<Train> trains = trainDao.getTrainsBetweenStations(1L, 5L, date);
		
		for (Train train : trains) {
			System.out.println(train.getTrainNo());
		}
		
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
	}
	
}
