<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	
	<jsp:include page="header.jsp" flush="true"/>
	
	<!-- Page header -->
	<div class="row reset">
		<div class="col-lg-4 rightpadding">
			<h4><label for="sendEditRoute">Station ${param.station} on date ${param.chosenDate}</label></h4>
		</div>
	</div>
	
	<div class="col-lg-12">
		<c:choose>
        	<c:when test="${not empty scheduleList}">
	        	<table class="table table-striped" id="forwardTrains">
	                <thead>
	                    <tr>
	                        <td>Train #</td>
	                        <td>Arrival time</td>
	                        <td>Departure time</td>
	                    </tr>
	                </thead>
	                <c:forEach var="schedule" items="${scheduleList}">
	                    <tr>
	                        <td>${schedule.train.trainNo}</td>
	                        <td>
                        		<c:if test="${not empty schedule.arrivalTime}">
                        			<fmt:formatDate value="${schedule.arrivalTime}" pattern="HH:mm:ss"/>
                        		</c:if>
	                        </td>
	                        <td>
	                        	<c:if test="${not empty schedule.departureTime}">
                        			<fmt:formatDate value="${schedule.departureTime}" pattern="HH:mm:ss"/>
                        		</c:if>
	                        </td>
	                    </tr>
	                </c:forEach>
	            </table>  
	        </c:when>
	        <c:otherwise>
	            <br>        
	            <div class="alert alert-info">
	                No schedules were found matching your search criteria.
	            </div>
	        </c:otherwise>
	    </c:choose>
    </div>
</body>

</html>