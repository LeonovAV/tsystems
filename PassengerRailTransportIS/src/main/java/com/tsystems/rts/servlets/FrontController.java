package com.tsystems.rts.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.utils.BusinessLogicException;

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
		resp.setHeader("Cache-Control", "no-cache");
		this.processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache");
		this.processRequest(req, resp);
	}
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Contains name of the result page
		String resultPage;
		
		try {
			// Analyze request and create command
			Command command = RequestHelper.getCommand(req);
			
			// Set result page
			resultPage = command.process(req, resp);
		} 
		catch (BusinessLogicException e) {
			// In case of unknown command or other exception
			// TODO Log exception
			resultPage = "error.html";
		}
		catch (Exception e) {
			// In case of unknown command or other exception
			// TODO Log exception
			resultPage = "error.html";
		}
		
		// Redirect to the result page
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
		dispatcher.forward(req, resp);
	}
	
}
