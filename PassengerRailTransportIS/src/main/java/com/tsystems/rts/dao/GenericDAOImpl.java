package com.tsystems.rts.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
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
	
	@SuppressWarnings("unchecked")
	public T findObject(Query query) {
		T t = null;
		t = (T) query.uniqueResult();
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findObjects(Query query) {
		return (List<T>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllObjects(Class<T> clazz) {
		Session session = this.getSession();
		Query query = session.createQuery("FROM " + clazz.getName());
		return (List<T>) query.list();
	}
	
	protected Session getSession() {
		return HibernateUtil.getSession();
	}
	
}
