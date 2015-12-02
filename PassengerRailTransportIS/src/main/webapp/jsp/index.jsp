<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	
	<jsp:include page="header.jsp" flush="true"/>
	
	<div class="container">
		<ul class="nav nav-tabs">
		    <li class="active"><a data-toggle="pill" href="#trains">Find trains</a></li>
		    <li><a data-toggle="pill" href="#schedules">Find schedules</a></li>
	 	</ul>
		<div class="tab-content">
			<div id="trains" class="tab-pane fade in active">
				<jsp:include page="findTrains.jsp" flush="true"/>
		  	</div>
		   	<div id="schedules" class="tab-pane fade">
		   		<jsp:include page="findScheduleForm.jsp" flush="true"/>
		   	</div>
		</div>
  	</div>
  	
  	<div class="row reset">
  		<p>${errorMsg}</p>
  	</div>
  	
</body>

</html>
