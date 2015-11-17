package com.tsystems.rts.dao;

import java.io.Serializable;

import org.hibernate.Query;

/**
 * Interface for common methods for all DAO objects.
 * @author Anton
 * @version 0.0.1
 *
 */
public interface GenericDAO<T, ID extends Serializable> {
	
	/**
	 * Save a newInstance object in the DB
	 * @param newInstance
	 * @return primary key for new row in DB
	 */
	ID save(T newInstance);
	
	/**
	 * Find instance based on primary key
	 * @param clazz type of object to find
	 * @param id object primary key
	 * @return existed instance, otherwise null
	 */
	T findById(Class<T> clazz, ID id);
	
	/**
	 * Delete instance from DB
	 * @param instance object, that will be deleted from data store
	 */
	void delete(T instance);
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	T findObject(Query query);
	
}
