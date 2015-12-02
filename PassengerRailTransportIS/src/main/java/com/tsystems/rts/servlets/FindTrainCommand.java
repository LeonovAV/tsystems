package com.tsystems.rts.servlets;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.entities.Train;
import com.tsystems.rts.services.ServiceLocator;
import com.tsystems.rts.services.TrainService;
import com.tsystems.rts.utils.ServiceException;
import com.tsystems.rts.utils.Utility;

public class FindTrainCommand implements Command {

	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
		try {
			// Get parameters from request
			long firstStationId = Long.parseLong(req.getParameter("firstStationId"));
			long lastStationId = Long.parseLong(req.getParameter("lastStationId"));
			
			String departure = req.getParameter("departureDate");
			
			// Convert to date
			Date departureDate = Utility.convertString(departure);
		
			// Get train service
			TrainService service = ServiceLocator.INSTANCE.getTrainService();
			
			// Find forward trains, which are satisfied the request
			List<Train> forwardTrains = service.findTrains(firstStationId, lastStationId, departureDate);
			
			// Get back departure date
			String backDeparture = req.getParameter("backDepartureDate");
			
			List<Train> backTrains = null;
			Date backDepartureDate = null;
			if (backDeparture != null && !backDeparture.isEmpty()) {
				backDepartureDate = Utility.convertString(backDeparture);
				// Find back trains, which are satisfied the request
				backTrains = service.findTrains(lastStationId, firstStationId, backDepartureDate);
			}
			
			// Set parameters for the page
			req.setAttribute("backDepartureDate", backDepartureDate);
			req.setAttribute("forwardTrainList", forwardTrains);
			req.setAttribute("backTrainList", backTrains);
		
		} catch (ParseException e) {
			throw new ServiceException("Date is not valid", e);
		}
		
		return "/jsp/viewTrains.jsp";
	}
	
}
