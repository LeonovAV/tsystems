package com.tsystems.rts.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.tsystems.rts.utils.DAOException;
import com.tsystems.rts.utils.HibernateUtil;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	@SuppressWarnings("unchecked")
	public ID save(T newInstance) throws DAOException {
		try {
			Session session = this.getSession();
			ID generatedId = (ID) session.save(newInstance);
			return generatedId;
		} catch (HibernateException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException ex = (ConstraintViolationException) e.getCause();
				throw new DAOException("Can not save the same instance in the DB", ex);
			};
			throw new DAOException("Can not save instance in the DB", e);
		}
	}

	public T findById(Class<T> clazz, ID id) throws DAOException {
		try {
			Session session = this.getSession();
			return session.get(clazz, id);
		} catch (HibernateException e) {
			throw new DAOException("Can not find instance by ID in the DB", e);
		}
	}

	public void delete(T instance) throws DAOException {
		try {
			Session session = this.getSession();
			session.delete(instance);
		} catch (HibernateException e) {
			throw new DAOException("Can not delete instance from the DB", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T findObject(Query query) throws DAOException {
		try {
			T t = null;
			t = (T) query.uniqueResult();
			return t;
		} catch (HibernateException e) {
			throw new DAOException("Can not find object by query in the DB", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findObjects(Query query) throws DAOException {
		try {
			return (List<T>) query.list();
		} catch (HibernateException e) {
			throw new DAOException("Can not find objects by query in the DB", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllObjects(Class<T> clazz) throws DAOException {
		try {
			Session session = this.getSession();
			Query query = session.createQuery("FROM " + clazz.getName());
			return (List<T>) query.list();
		} catch (HibernateException e) {
			throw new DAOException("Can not find all objects in the DB", e);
		}
	}
	
	protected Session getSession() throws DAOException {
		return HibernateUtil.getSession();
	}
	
}
