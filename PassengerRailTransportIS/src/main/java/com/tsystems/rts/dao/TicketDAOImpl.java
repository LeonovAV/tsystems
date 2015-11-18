package com.tsystems.rts.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;

import com.tsystems.rts.entities.Ticket;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class TicketDAOImpl extends GenericDAOImpl<Ticket, Long> implements TicketDAO {

	public List<Ticket> getPurchasedTicketsForTrain(long trainId, Timestamp trainDepartureDate) {
		String sqlQuery = "SELECT t FROM Ticket t WHERE t.train.trainId = :trainId "
				+ "AND t.trainDepartureDate = :trainDepartureDate";
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("trainId", trainId).setParameter("trainDepartureDate", trainDepartureDate);
		return findObjects(query);
	}
	
	public static void main(String[] args) {
		HibernateUtil.beginTransaction();
		List<Ticket> tickets = new TicketDAOImpl().getPurchasedTicketsForTrain(2, Timestamp.valueOf("2015-11-14 17:10:00"));
		System.out.println(tickets.size());
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
	}
	
}
