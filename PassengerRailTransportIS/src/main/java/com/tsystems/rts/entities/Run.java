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
		Session session = HibernateUtil.beginTransaction();
		// Find passenger by id
		Passenger passenger = (Passenger)session.get(Passenger.class, 1L);
		System.out.println(passenger.getFirstName() + " " + passenger.getLastName());
		// Get all tickets for this passenger
		List<Ticket> list = passenger.getPurchasedTickets();
		for (Ticket ticket : list) {
			System.out.println(ticket.getTicketId() + " " + ticket.getTrainArrivalDate());
		}
		
		HibernateUtil.commitTransaction();
		
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
	}

}
