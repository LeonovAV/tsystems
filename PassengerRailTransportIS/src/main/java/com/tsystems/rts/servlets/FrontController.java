package com.tsystems.rts.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tsystems.rts.utils.ServiceException;

/**
 * Front controller pattern. Manage all requests to the system.
 * @author Anton
 * @version 0.0.1
 *
 */
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 2491343650391857276L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.processRequest(req, resp);
	}
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Contains name of the result page
		String resultPage;
		
		RequestDispatcher dispatcher;
		
		// Check if there is a problem with database connection
		String dbError = (String) getServletContext().getAttribute("dbError");
		
		System.out.println(dbError);
		
		if (dbError != null) {
			req.setAttribute("errorMsg", dbError);
			// Redirect to the error page before processing starts
			// HibernateUtil class could no be initialized
			dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
			dispatcher.forward(req, resp);
		}
		
		try {
			// Analyze request and create command
			Command command = RequestHelper.getCommand(req);
			
			// Set result page
			resultPage = command.process(req, resp);
		} 
		catch (ServiceException e) {
			Logger.getLogger("LOG-FILE-APPENDER").error("Application exception occurs", e);
			req.setAttribute("errorMsg", e.getCause().getMessage());
			resultPage = "/jsp/error.jsp";
		}
		
		// Redirect to the result page
		dispatcher = getServletContext().getRequestDispatcher(resultPage);
		dispatcher.forward(req, resp);
	}
	
}
