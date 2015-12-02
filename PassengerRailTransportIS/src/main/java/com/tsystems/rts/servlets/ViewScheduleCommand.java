package com.tsystems.rts.servlets;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.entities.Schedule;
import com.tsystems.rts.services.ScheduleService;
import com.tsystems.rts.services.ServiceLocator;
import com.tsystems.rts.utils.ServiceException;
import com.tsystems.rts.utils.Utility;

public class ViewScheduleCommand implements Command {

	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
		try {
			// Get request parameters
			long stationId = Long.parseLong(req.getParameter("stationId"));
			Date chosenDate = Utility.convertString(req.getParameter("chosenDate"));
			
			// Get service and schedules based on request
			ScheduleService service = ServiceLocator.INSTANCE.getScheduleService();
			List<Schedule> schedules = service.viewSchedule(stationId, chosenDate);
			
			// Set attribute to forward
			req.setAttribute("scheduleList", schedules);
		} catch (ParseException e) {
			// Log exception and throw custom
		}
		
		return "/jsp/viewSchedule.jsp";
	}

}
