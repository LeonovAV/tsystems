<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

	<jsp:include page="header.jsp" flush="true"/>
	
	<div class="row reset">
		<div class="col-lg-8 rightpadding">
			<p>Passenger: ${param.firstName}, ${param.lastName}, ${param.birthdate} successfully purchased ticket(s).</p>
			<p>Train number ${param.trainNoForward}, departure time: ${param.departureTimeForward}</p>
			<c:if test="${not empty param.trainNoBack}">
				<p>Train number ${param.trainNoBack}, departure time: ${param.departureTimeBack}</p>
			</c:if>
		</div>
	</div>
</body>
</html>