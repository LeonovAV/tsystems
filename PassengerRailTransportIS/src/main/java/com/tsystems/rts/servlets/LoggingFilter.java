package com.tsystems.rts.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LoggingFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		
		HttpSession session = req.getSession(false);
		String login = null;
		if (session != null) {
			login = (String) session.getAttribute("login");
		}
        
		if (session == null || login == null) {
			Logger.getLogger("LOG-FILE-APPENDER").info("Unauthorized access");
			// Redirect to the result page
			req.setAttribute("errorMsg", "Unauthorized access");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/error.jsp");
			dispatcher.forward(req, resp);
		}
		
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		Logger.getLogger("LOG-FILE-APPENDER").info("LoggingFilter has initialized");
	}
	
}
