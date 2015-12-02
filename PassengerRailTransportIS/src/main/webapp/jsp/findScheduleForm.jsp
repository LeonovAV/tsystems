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
	<form role="form" method="post" id="findSchedule">
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
				<label style="display:none;" id="msg">No matches found</label>
			</div>
			<div class="col-lg-4 rightpadding">
				<input type="date" class="form-control large-input" id="chosenDate" name="chosenDate">
			</div>
			<div class="col-lg-2 rightpadding">
				<input type="hidden" name="command" value="ViewSchedule">
				<input type="hidden" id="stationId" name="stationId" value="">
				<input type="submit" class="btn btn-info large-input" value="View schedule">
			</div>
		</div>
	</form>
</body>

<script>

	// Autocomplete form for stations
	$(document).ready(function() {

		var stations = [];

		<c:forEach var="station" items="${stationList}">
			stations.push({
				"id": "${station.stationId}",
		        "label": "${station.name}",
			    "value": "${station.name}"
			});
		</c:forEach>
		
		$("#station").autocomplete({
			source: stations,
			minLength: 1,
			select: function(event, ui) {
				$("#stationId").val(ui.item.id);
			},
			change: function( event, ui ) {
				if (!ui.item) {
					$(this).val('');
					$("#msg").show().delay(2000).fadeOut();
				}
			}
		});

		$("#findSchedule").validate({
		    rules: {
				station: {
					required: true
				},
				
				chosenDate: {
					required: true
				}
			}
		});
		
	});
	
</script>

</html>