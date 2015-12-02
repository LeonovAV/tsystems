package com.tsystems.rts.dao;

import com.tsystems.rts.entities.Worker;
import com.tsystems.rts.utils.DAOException;

public interface WorkerDAO extends GenericDAO<Worker, Long> {
	
	/**
	 * Find worker by unique login
	 * @param login
	 * @return worker entity
	 */
	Worker findByLogin(String login) throws DAOException;
	
}
