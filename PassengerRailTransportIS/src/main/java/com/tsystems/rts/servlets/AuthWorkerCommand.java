package com.tsystems.rts.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.tsystems.rts.services.ServiceLocator;
import com.tsystems.rts.utils.ServiceException;

public class AuthWorkerCommand implements Command {

	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
		
		// Get parameters from request
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		
		// Validate parameters
		String errorMsg = ServiceLocator.INSTANCE.getWorkerService().checkWorker(login, password);
		
		if (!errorMsg.isEmpty()) {
			req.setAttribute("errorMsg", errorMsg);
			return "/jsp/index.jsp";
		}
		
		// Set session attribute
		HttpSession session = req.getSession();
		session.setAttribute("login", login);
		session.setMaxInactiveInterval(30*60);
		Logger.getLogger("LOG-FILE-APPENDER").info("Worker " + login + " has successfully signed in.");
		
		return "/jsp/workers/index.jsp";
	}
	
}
