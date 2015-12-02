package com.tsystems.rts.services;

import com.tsystems.rts.dao.WorkerDAO;
import com.tsystems.rts.dao.WorkerDAOImpl;
import com.tsystems.rts.entities.Worker;
import com.tsystems.rts.utils.DAOException;
import com.tsystems.rts.utils.HibernateUtil;
import com.tsystems.rts.utils.ServiceException;

public enum WorkerService {
	
	INSTANCE;
	
	private WorkerService() {
	}
	
	public String checkWorker(String login, String password) throws ServiceException {
		// Initialize DAO
		WorkerDAO wDao = new WorkerDAOImpl();
		try {
			HibernateUtil.beginTransaction();
			// Find worker by login
			Worker worker = wDao.findByLogin(login);
			if (worker == null) {
				HibernateUtil.rollbackTransaction();
				return "Can not find worker with this login and password";
			}
			// Check password
			if (!password.equals(worker.getPassword())) {
				HibernateUtil.rollbackTransaction();
				return "Password is not correct";
			}
			HibernateUtil.commitTransaction();
			
			return "";
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
