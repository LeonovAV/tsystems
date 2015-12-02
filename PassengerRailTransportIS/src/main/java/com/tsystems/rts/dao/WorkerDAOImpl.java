package com.tsystems.rts.dao;

import org.hibernate.Query;

import com.tsystems.rts.entities.Worker;
import com.tsystems.rts.utils.DAOException;
import com.tsystems.rts.utils.HibernateUtil;

public class WorkerDAOImpl extends GenericDAOImpl<Worker, Long> implements WorkerDAO {

	public Worker findByLogin(String login) throws DAOException {
		String sqlQuery = "SELECT w FROM Worker w WHERE w.login = :login";
		Query query = HibernateUtil.getSession().createQuery(sqlQuery)
				.setParameter("login", login);
		return findObject(query);
	}
	
}
