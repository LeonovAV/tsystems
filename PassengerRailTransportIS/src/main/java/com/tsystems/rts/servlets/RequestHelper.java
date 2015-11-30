package com.tsystems.rts.servlets;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Anton
 * @version 0.0.1
 *
 */
public class RequestHelper {
	
	public static Command getCommand(HttpServletRequest request) throws Exception {
		String commandReq = request.getParameter("command");
		
		// Starting page
		if (commandReq == null || commandReq.isEmpty()) {
			commandReq = "Home";
		}
		
		// Create class based on command
		return (Command) Class.forName("com.tsystems.rts.servlets." + commandReq + "Command").newInstance();
	}
	
}
