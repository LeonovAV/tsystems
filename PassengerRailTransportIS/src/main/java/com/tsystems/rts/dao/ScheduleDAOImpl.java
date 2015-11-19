package com.tsystems.rts.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.tsystems.rts.entities.Schedule;
import com.tsystems.rts.utils.HibernateUtil;

public class ScheduleDAOImpl extends GenericDAOImpl<Schedule, Long> implements ScheduleDAO {

	public List<Schedule> getAllSchedules(long stationId, Date date) {
		String sqlQuery = "SELECT s FROM Schedule s WHERE s.station.stationId = :stationId "
				+ "AND DATE(s.departureTime) = :departureTime";
		
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("stationId", stationId)
				.setParameter("departureTime", date);
		
		return findObjects(query);
	}
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(2015, Calendar.NOVEMBER, 14);
		Date date = cal.getTime();
		cal.clear();
		HibernateUtil.beginTransaction();
		System.out.println(new ScheduleDAOImpl().getAllSchedules(1L, date).size());
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
	}

}
