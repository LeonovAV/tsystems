package com.tsystems.rts.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.tsystems.rts.utils.ServiceException;

public class LogOutCommand implements Command {

	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
		// Invalidate the session if exists
        HttpSession session = req.getSession(false);
        
        String login = null;
        
        if (session != null) {
        	login = (String) session.getAttribute("login");
            session.invalidate();
        }
        
        Logger.getLogger("LOG-FILE-APPENDER").info("Worker " + login + "logged out.");
        
		return "/jsp/index.jsp";
	}

}
