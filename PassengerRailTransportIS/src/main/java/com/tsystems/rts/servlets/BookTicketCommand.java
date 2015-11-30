package com.tsystems.rts.servlets;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.services.ServiceLocator;
import com.tsystems.rts.services.TicketService;
import com.tsystems.rts.utils.BusinessLogicException;
import com.tsystems.rts.utils.Utility;

/**
 * Book tickets for chosen trains. Open form with personal data of
 * passenger.
 * @author Anton
 * @version 0.0.1
 *
 */
public class BookTicketCommand implements Command {

	public String process(HttpServletRequest req, HttpServletResponse resp) throws BusinessLogicException {
		System.out.println("In Book Ticket Command");
		try {
			// Get passenger data
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			Date birthdate = Utility.convertString(req.getParameter("birthdate"));
			
			System.out.println("Name " + firstName + "Date " + birthdate);
			
			// Get train parameters from request
			long trainIdForward = Long.parseLong(req.getParameter("trainIdForward"));
			
			System.out.println(trainIdForward);
			
			Timestamp departureTimeForward = Timestamp.valueOf(req.getParameter("departureTimeForward"));
			
			System.out.println("Date: " + departureTimeForward);
			
			// Use service to buy ticket
			TicketService service = ServiceLocator.INSTANCE.getTicketService();
						
			// Buy ticket for the forward train
			service.purchaseTicket(trainIdForward, departureTimeForward, firstName, lastName, birthdate);
			
			String trainBack = req.getParameter("trainIdBack");
			String departureBack = req.getParameter("departureTimeBack");
			
			System.out.println(trainBack + "Date: " + departureBack);
			
			boolean isTrainBackValid = trainBack != null && !trainBack.isEmpty();
			boolean isDepartureBackValid = departureBack != null && !departureBack.isEmpty();
			
			long trainIdBack = 0;
			Timestamp departureTimeBack = null;
			if (isTrainBackValid && isDepartureBackValid) {
				trainIdBack = Long.parseLong(trainBack);
				departureTimeBack = Timestamp.valueOf(trainBack);
				// Buy ticket for the back train
				service.purchaseTicket(trainIdBack, departureTimeBack, firstName, lastName, birthdate);
			}
		}
		catch (ParseException e) {
			// Log exception
		}
		return "/jsp/successTicketBooking.jsp";
	}

}
