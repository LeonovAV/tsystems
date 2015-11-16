package com.tsystems.rts.dao;

import java.io.Serializable;

import org.hibernate.Session;

import com.tsystems.rts.utils.HibernateUtil;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	@SuppressWarnings("unchecked")
	public ID save(T newInstance) {
		Session session = this.getSession();
		ID generatedId = (ID) session.save(newInstance);
		return generatedId;
	}

	public T findById(Class<T> clazz, ID id) {
		Session session = this.getSession();
		return session.get(clazz, id);
	}

	public void delete(T instance) {
		Session session = this.getSession();
		session.delete(instance);
	}
	
	protected Session getSession() {
		return HibernateUtil.getSession();
	}
	
}
