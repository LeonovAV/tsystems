package com.tsystems.rts.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.entities.Train;
import com.tsystems.rts.services.ServiceLocator;
import com.tsystems.rts.services.TrainService;

/**
 * Class for showing simple string to test environment
 * @author Anton
 * @version 0.0.1
 *
 */

public class TestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 8810213357636837414L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		TrainService service = ServiceLocator.INSTANCE.getTrainService();
		List<Train> trains = service.viewAllTrains();
		int size = trains.size();
		
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h2>Hello from Simple Servlet</h2>");
		out.println("<p>" + size + "</p>");
		out.println("</body>");
		out.println("</html>");
		
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/html;charset=utf-8");
	}
	
}
