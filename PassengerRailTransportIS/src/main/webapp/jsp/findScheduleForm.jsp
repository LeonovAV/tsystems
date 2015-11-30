<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<form role="form" method="post">
		<div class="row reset">
			<div class="col-lg-6 rightpadding">
				Choose station
			</div>
			<div class="col-lg-2 rightpadding">
				Date
			</div>
		</div>
		
  		<div class="row reset">
			<div class="col-lg-6 rightpadding">
				<input type="text" class="form-control large-input" id="station" name="station" placeholder="Station">
			</div>
			<div class="col-lg-2 rightpadding">
				<input type="date" class="form-control large-input" id="chosenDate" name="chosenDate">
			</div>
			<div class="col-lg-2 rightpadding">
				<input type="hidden" name="command" value="ViewSchedule">
				<input type="submit" class="btn btn-info large-input" value="View schedule">
			</div>
		</div>
	</form>
</body>

<script>

	// Set minimum date for choosing
	var currentDate = new Date();
	
	var day = currentDate.getDate();
	var month = currentDate.getMonth()+1;
	var year = currentDate.getFullYear();
				
	if (month < 10)
		month = "0" + month;
		
	if (day < 10)
		day = "0" + day;
				
	var today = year + "-" + month + "-" + day;
	
	$("#chosenDate").prop('min', today);
	
</script>

</html>