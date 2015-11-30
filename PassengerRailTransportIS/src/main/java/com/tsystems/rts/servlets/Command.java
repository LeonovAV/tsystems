package com.tsystems.rts.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.rts.utils.BusinessLogicException;

/**
 * 
 * @author Anton
 *
 */
public interface Command {
	/**
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws BusinessLogicException 
	 */
	String process(HttpServletRequest req, HttpServletResponse resp) throws BusinessLogicException;
	
}
