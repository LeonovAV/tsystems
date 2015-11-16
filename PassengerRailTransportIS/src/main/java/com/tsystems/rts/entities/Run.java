package com.tsystems.rts.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tsystems.rts.utils.HibernateUtil;

public class Run {

	public static void main(String[] args) {
//		Session session = HibernateUtil.getSession();
//		Transaction transaction = session.beginTransaction();
//		Passenger passenger = new Passenger();
//		passenger.setFirstName("Anton");
//		passenger.setLastName("Leonov");
//		Calendar cal = Calendar.getInstance();
//		cal.set(1992, Calendar.JANUARY, 9);
//		Date date = cal.getTime();
//		cal.clear();
//		passenger.setBirthdate(date);
//		session.save(passenger);
//		transaction.commit();
//		HibernateUtil.closeSession();
//		HibernateUtil.closeSessionFactory();
		
		// Test one to many
//		Session session = HibernateUtil.beginTransaction();
//		// Find passenger by id
//		Passenger passenger = (Passenger)session.get(Passenger.class, 1L);
//		System.out.println(passenger.getFirstName() + " " + passenger.getLastName());
//		// Get all tickets for this passenger
//		List<Ticket> list = passenger.getPurchasedTickets();
//		for (Ticket ticket : list) {
//			System.out.println(ticket.getTicketId() + " " + ticket.getTrainArrivalDate());
//		}
//		
//		HibernateUtil.commitTransaction();
//		
//		HibernateUtil.closeSession();
//		HibernateUtil.closeSessionFactory();
		
//		Session session = HibernateUtil.beginTransaction();
//		// Find train by ID
//		Train train = session.get(Train.class, 2L);
//		System.out.println(train.getTrainId() + " " + train.getTrainNo());
//		// Get all tickets
//		List<Ticket> tickets = train.getTrainTickets();
//		for (Ticket ticket : tickets) {
//			System.out.println(ticket.getTicketId() + " " + ticket.getTrainArrivalDate());
//		}
//		HibernateUtil.commitTransaction();
//		
//		HibernateUtil.closeSession();
//		HibernateUtil.closeSessionFactory();
		
//		Session session = HibernateUtil.beginTransaction();
//		// Find train by ID
//		Train train = session.get(Train.class, 2L);
//		System.out.println(train.getTrainId() + " " + train.getTrainNo());
//		List<Station> route = train.getRoute();
//		for (Station station : route) {
//			System.out.println(station.getStationId() + " " + station.getName());
//		}
//		
//		session.delete(route.get(1));
//		
//		HibernateUtil.commitTransaction();
//		HibernateUtil.closeSession();
//		HibernateUtil.closeSessionFactory();
		
//		Session session = HibernateUtil.beginTransaction();
//		// Find train by ID
//		Train train = session.get(Train.class, 2L);
//		System.out.println(train.getTrainId() + " " + train.getTrainNo());
//		List<Schedule> schedules = train.getSchedules();
//		for (Schedule schedule : schedules) {
//			System.out.println(schedule.getScheduleId() + " " + schedule.getArrivalTime());
//		}
//		
//		HibernateUtil.commitTransaction();
//		HibernateUtil.closeSession();
//		HibernateUtil.closeSessionFactory();
		
		Session session = HibernateUtil.beginTransaction();
		// Find station by ID
		Station station = session.get(Station.class, 1L);
		System.out.println(station.getStationId() + " " + station.getName());
		List<Schedule> schedules = station.getSchedules();
		for (Schedule schedule : schedules) {
			System.out.println(schedule.getScheduleId() + " " + schedule.getArrivalTime());
		}
		
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
		
	}

}