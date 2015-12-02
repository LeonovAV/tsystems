<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/smoothness/jquery-ui.css" />
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/rts.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
	<title>Passenger Rail Transport System</title>
</head>
<body>
	<!-- Logo, authorization -->
	<div class="row reset">
		<div class="col-lg-6">
			<img src="img/trainLogo.png" alt="Train view">
		</div>
		<div class="col-lg-6">
			<c:choose>
				<c:when test="${not empty login}">
					<p>Worker: ${login}</p>
					<form role="form" method="post">
						<input type="hidden" name="command" value="LogOut">
						<input type="submit" class="btn btn-info" value="Log out">
					</form>
				</c:when>
				<c:otherwise>
					<button type="button" class="btn btn-info" data-toggle="modal" data-target="#authModal">Workers</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<!-- Modal view -->
	<div id="authModal" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm">

			<!-- Modal content (auth form) -->
			<jsp:include page="authForm.jsp" flush="true"/>
			
		</div>
	</div>
</body>

</html>