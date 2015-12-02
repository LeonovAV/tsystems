package com.tsystems.rts.servlets;

import javax.servlet.http.HttpServletRequest;

import com.tsystems.rts.utils.ServiceException;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class RequestHelper {
	
	public static Command getCommand(HttpServletRequest request) throws ServiceException {
		String commandReq = request.getParameter("command");
		
		// Starting page
		if (commandReq == null || commandReq.isEmpty()) {
			commandReq = "Home";
		}
		
		// Create class based on command
		try {
			return (Command) Class.forName("com.tsystems.rts.servlets." + commandReq + "Command").newInstance();
		} catch (InstantiationException e) {
			throw new ServiceException("Can not instantiate Command class", e);
		} catch (IllegalAccessException e) {
			throw new ServiceException("Can not get access to the Command class", e);
		} catch (ClassNotFoundException e) {
			throw new ServiceException("Can not initialize Command class. Class not found.", e);
		}
	}
	
}
