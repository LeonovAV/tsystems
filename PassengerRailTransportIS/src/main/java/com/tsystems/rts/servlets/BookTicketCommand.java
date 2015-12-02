package com.tsystems.rts.servlets;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.services.ServiceLocator;
import com.tsystems.rts.services.TicketService;
import com.tsystems.rts.utils.ServiceException;
import com.tsystems.rts.utils.Utility;

/**
 * Book tickets for chosen trains. Open form with personal data of
 * passenger.
 * @author Anton
 * @version 0.0.1
 *
 */
public class BookTicketCommand implements Command {

	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
		try {
			// Get passenger data
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			Date birthdate = Utility.convertString(req.getParameter("birthdate"));
			
			// Get forward train parameters from request
			long trainIdForward = Long.parseLong(req.getParameter("trainIdForward"));
			
			Timestamp departureTimeForward = Timestamp.valueOf(req.getParameter("departureTimeForward"));
			
			// Get back train parameters if exist
			String trainBack = req.getParameter("trainIdBack");
			String departureBack = req.getParameter("departureTimeBack");
			
			boolean isTrainBackValid = trainBack != null && !trainBack.isEmpty();
			boolean isDepartureBackValid = departureBack != null && !departureBack.isEmpty();
			
			long trainIdBack = 0;
			Timestamp departureTimeBack = null;
			if (isTrainBackValid && isDepartureBackValid) {
				trainIdBack = Long.parseLong(trainBack);
				departureTimeBack = Timestamp.valueOf(departureBack);
			}
			
			// Use service to buy ticket
			TicketService service = ServiceLocator.INSTANCE.getTicketService();
			
			// Buy ticket for the trains
			String errorMsg = service.purchaseTicket(trainIdForward, departureTimeForward, trainIdBack, departureTimeBack, 
					firstName, lastName, birthdate);
			
			if (!errorMsg.isEmpty()) {
				req.setAttribute("errorMsg", errorMsg);
				return "/jsp/error.jsp";
			}
		}
		catch (ParseException e) {
			throw new ServiceException("Date is not valid", e);
		}
		catch (IllegalArgumentException e) {
			throw new ServiceException("Date format is not valid", e);
		}
		return "/jsp/successTicketBooking.jsp";
	}

}
