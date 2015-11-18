package com.tsystems.rts.services;

import java.util.concurrent.TimeUnit;

import com.tsystems.rts.dao.TrainDAO;
import com.tsystems.rts.dao.TrainDAOImpl;
import com.tsystems.rts.entities.Train;
import com.tsystems.rts.utils.HibernateUtil;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class TrainService {
	
	public void findTrains(long trainId) {
		TrainDAO trainDao = new TrainDAOImpl();
		// Check train date
		Train train = trainDao.findById(Train.class, trainId);
		int period = train.getPeriod();
		long startingDate = train.getStartingDate().getTime();
		long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - startingDate);
		
		if (days % period != 0) {
			System.out.println("Can not buy ticket, because there is no train for this date");
			HibernateUtil.rollbackTransaction();
		}
	}
	
}
