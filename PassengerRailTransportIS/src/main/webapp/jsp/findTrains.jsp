<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.rts.entities.*" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>

	<!-- Set train parameters and try to find them -->
	
	<form role="form" method="post" id="findTrainForm">
		
		<div class="row reset">
			<div class="col-lg-8 rightpadding">
				Choose stations
			</div>
			<div class="col-lg-2 rightpadding">
				Arrival date
			</div>
		</div>
		
  		<div class="row reset">
			<div class="col-lg-8 rightpadding">
				<div class="col-lg-6 rightpadding">
					<input type="hidden" id="firstStationId" name="firstStationId" value="">
					<input type="text" class="form-control large-input" id="from" name="firstStation" placeholder="From">
					<label style="display:none;" id="msgForward">No matches found</label>
				</div>	
				<div class="col-lg-1">
				</div>
				<div class="col-lg-6 rightpadding">
					<input type="hidden" id="lastStationId" name="lastStationId" value="">
					<input type="text" class="form-control large-input" id="to" name="lastStation" placeholder="To">
					<label style="display:none;" id="msgBack">No matches found</label>
				</div>
			</div>
			<div class="col-lg-4 rightpadding">
				<input type="date" class="form-control large-input" id="departureDate" name="departureDate">
			</div>
		</div>
		
		<div class="row reset">
			<div class="col-lg-8 rightpadding">
				<div class="pull-right rightpadding"><label><input type="checkbox" id="isBackNeeded" name="isBackNeeded" value="back">Back</label></div>
			</div>
			<div class="col-lg-4 rightpadding">
				<input type="date" class="form-control large-input" id="backDepartureDate" name="backDepartureDate" disabled>
			</div>
		</div>
		
		<div class="row reset">
			<div class="col-lg-12 rightpadding">
				<div class="pull-right">
					<input type="hidden" name="command" value="FindTrain">
					<input type="submit" class="btn btn-info large-input" value="Search">
				</div>
			</div>
		</div>
	</form>
	
</body>

<script>
	// Enable or disable back route
	$("#isBackNeeded").change(function(){
		if ($("#isBackNeeded").is(':checked') == true){
			$("#backDepartureDate").prop('disabled', false);
		} else {
			$("#backDepartureDate").prop('disabled', true);
		}
	});
	
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
	
	$("#departureDate").prop('min', today);
	$("#backDepartureDate").prop('min', today);

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
		
		$("#from").autocomplete({
			source: stations,
			minLength: 1,
			select: function(event, ui) {
				$("#firstStationId").val(ui.item.id);
			},
			change: function( event, ui ) {
				if (!ui.item) {
					$(this).val('');
					$("#msgForward").show().delay(2000).fadeOut();
				}
			}
		});
		
		$("#to").autocomplete({
			source: stations,
			minLength: 1,
			select: function(event, ui) {
				$("#lastStationId").val(ui.item.id);
			},
			change: function( event, ui ) {
				if (!ui.item) {
					$(this).val('');
					$("#msgBack").show().delay(2000).fadeOut();
				}
			}	
		});

		$("#findTrainForm").validate({
		    rules: {
				firstStation: {
					required: true
				},
				
				lastStation: {
					required: true
				},
			  
				departureDate: {
					required: true
				},
				
				backDepartureDate: {
					required: true
				}
		    }
		});  
	});

</script>


</html>