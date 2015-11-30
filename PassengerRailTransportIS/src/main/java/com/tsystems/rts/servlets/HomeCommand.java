package com.tsystems.rts.servlets;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.entities.Station;
import com.tsystems.rts.services.ServiceLocator;
import com.tsystems.rts.services.StationService;
import com.tsystems.rts.utils.BusinessLogicException;

/**
 * Class presents home page for web. To represent home page "Home" command is used.
 * @author Anton
 * @version 0.0.1
 *
 */
public class HomeCommand implements Command {

	public String process(HttpServletRequest req, HttpServletResponse resp) throws BusinessLogicException {
		// Load all stations
		StationService service = ServiceLocator.INSTANCE.getStationService();
		List<Station> stations = service.getAllStations();
		
		req.setAttribute("stationList", stations);
		
		return "/jsp/index.jsp";
	}

}
