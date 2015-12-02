<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.rts.entities.*" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	
	<jsp:include page="header.jsp" flush="true"></jsp:include>
	
	<!-- Page for passengers, which allows to choose a train -->
	
	<!-- Forward trains from station A to B -->
	<div class="row reset">
		<label>Forward</label>
	</div>
	
	<input type="button" class="btn btn-info btn-sm" style="display: none;" 
			id="showAllTrains" value="Show all trains" 
			onclick="showAllVariants('forwardTrains', 'trainIdForward', 'departureTimeForward', 'showAllTrains', 'trainNoForward')">
	<div class="col-lg-12">
    	<c:choose>
        	<c:when test="${not empty forwardTrainList}">
	        	<table class="table table-striped" id="forwardTrains">
	                <thead>
	                    <tr>
	                        <td style="display:none"></td>
	                        <td></td>
	                        <td>Train #</td>
	                        <td>Seats number</td>
	                        <td>Starting date</td>
	                        <td>Period</td>
	                        <td>Actions</td>
	                    </tr>
	                </thead>
	                <c:forEach var="forwardTrain" items="${forwardTrainList}">
	                    <tr>
	                        <td style="display:none">${forwardTrain.trainId}</td>
	                        <td>
								<label class="checkbox-inline"><input type="radio"></label>
							</td>
	                        <td>${forwardTrain.trainNo}</td>
	                        <td>${forwardTrain.seatsNumber}</td>
	                        <td>${forwardTrain.startingDate}</td>
	                        <td>${forwardTrain.period}</td>
	                        <td>
	                        	<form method="post" id="viewRouteForm">
		                        	<div class="row tableinput">
		                        		<div class="pull-left">
											<input type="hidden" name="command" value="ViewRoute">
											<input type="hidden" name="forwardTrainId" value="${forwardTrain.trainId}">
											<input type="submit" class="btn btn-info" value="View train route">
										</div>
		                        	</div>
	                        	</form>
	                        </td>
	                    </tr>
	                </c:forEach>
	            </table>  
	        </c:when>
	        <c:otherwise>
	            <br>        
	            <div class="alert alert-info">
	                No trains were found matching your search criteria.
	            </div>
	        </c:otherwise>
	    </c:choose>                        
   	</div>
    
    <!-- Back trains from station B to A -->
    <c:if test="${not empty backDepartureDate}">
    	<div class="row reset">
			<label>Back</label>
		</div>
		<input type="button" class="btn btn-info btn-sm" style="display: none;" 
			id="showAllTrainsBack" value="Show all trains" 
			onclick="showAllVariants('trainsBack', 'trainIdBack', 'departureTimeBack', 'showAllTrainsBack', 'trainNoBack')">
			
		<div class="col-lg-12">
	    	<c:choose>
	        	<c:when test="${not empty backTrainList}">
		        	<table  class="table table-striped" id="trainsBack">
		                <thead>
		                    <tr>
		                        <td style="display:none"></td>
		                        <td></td>
		                        <td>Train #</td>
		                        <td>Seats number</td>
		                        <td>Starting date</td>
		                        <td>Period</td>
		                        <td>Actions</td>
		                    </tr>
		                </thead>
		                <c:forEach var="backTrain" items="${backTrainList}">
		                    <tr>
		                        <td style="display:none">${backTrain.trainId}</td>
		                        <td>
									<label class="checkbox-inline"><input type="radio"></label>
								</td>
		                        <td>${backTrain.trainNo}</td>
		                        <td>${backTrain.seatsNumber}</td>
		                        <td>${backTrain.startingDate}</td>
		                        <td>${backTrain.period}</td>
		                        <td>
		                        	<form method="post" id="viewBackRouteForm">
			                        	<div class="row tableinput">
			                        		<div class="pull-left">
												<input type="hidden" name="command" value="ViewRoute">
												<input type="hidden" name="backTrainId" value="${backTrain.trainId}">
												<input type="submit" class="btn btn-info" value="View train route">
											</div>
			                        	</div>
		                        	</form>
		                        </td>
		                    </tr>
		                </c:forEach>
		            </table>
		        </c:when>         
		        <c:otherwise>
		            <br>         
		            <div class="alert alert-info">
		                No trains were found matching your search criteria.
		            </div>
		        </c:otherwise>
		    </c:choose>                        
	   	</div>
    </c:if>
    
    <form role="form" id="bookTicketForm" method="post">
		<div class="row reset">
			<div class="pull-right">
				<input type="hidden" name="trainNoForward" id="trainNoForward" value="">
				<input type="hidden" name="trainNoBack" id="trainNoBack" value="">
				<input type="hidden" name="trainIdForward" id="trainIdForward" value="">
				<input type="hidden" name="departureTimeForward" id="departureTimeForward" value="">
				<input type="hidden" name="trainIdBack" id="trainIdBack" value="">
				<input type="hidden" name="departureTimeBack" id="departureTimeBack" value="">
				<button type="button" class="btn btn-info" data-toggle="modal" data-target="#bookTicketModal" onclick="setParameters()" id="purchaseTicket" disabled>Book ticket</button>
			</div>
		</div>
		
		<!-- Modal view for passenger personal data -->
		<div id="bookTicketModal" class="modal fade" role="dialog">
			<div class="modal-dialog modal-sm">
				<!-- Modal content (personal form) -->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" onclick="clearText()">&times;</button>
						<h4 class="modal-title">Personal data</h4>
					</div>
				
					<div class="modal-body">
						<div class="row reset">
							<div class="form-group col-lg-12 rightpadding" id="trainsInfo">
							</div>
						</div>
						<div class="row reset">
							<div class="form-group col-lg-12 rightpadding">
								<label for="firstName">First name:</label>
								<input type="text" class="form-control large-input" name="firstName" id="firstName" placeholder="First name">
							</div>
						</div>
						<div class="row reset">
							<div class="form-group col-lg-12 rightpadding">
								<label for="lastName">Last name:</label>
								<input type="text" class="form-control large-input" name="lastName" id="lastName" placeholder="Last name">
							</div>
						</div>
						<div class="row reset">
							<div class="form-group col-lg-12 rightpadding">
								<label for="birthdate">Birthdate:</label>
								<input type="date" class="form-control large-input" name="birthdate" id="birthdate">
							</div>
						</div>
					</div>
					
					<div class="modal-footer">
						<input type="hidden" name="command" value="BookTicket">
						<input type="submit" class="btn btn-default" value="Purchase ticket">
						<button type="button" class="btn btn-default" data-dismiss="modal" onClick="clearText()">Close</button>
					</div>
				</div>
			</div>
		</div>
		
	</form>
    
</body>

<script>

	$(document).ready(function() {
		hideRows('forwardTrains', 'trainIdForward', 'departureTimeForward', 'showAllTrains', 'trainNoForward');
		hideRows('trainsBack', 'trainIdBack', 'departureTimeBack', 'showAllTrainsBack', 'trainNoBack');
	});

	// Hide rows based on chosen train variant and set values to send
	function hideRows(trainTable, trainId, time, showTrains, trainNo) {
		$("#" + trainTable).find('tr').click(function() {
			var index = $(this).index() + 1;		
			if ($("#" + trainTable + " :input[type=radio]").is(':checked')) {
				// Hide all rows
				$("#" + trainTable + " tr").hide(); 
				// Show the clicked one and the header
				$("#" + trainTable + " tr:eq(" + index + ")").show();
				$("#" + trainTable + " tr:eq(0)").show();
				// Set values to hidden field
				// Set current departure date
				var date;
				<c:choose>
    				<c:when test="${not empty param.backDepartureDate}">
    					date = '${param.backDepartureDate}';
    				</c:when>
    				<c:otherwise>
    					date = '${param.departureDate}';
    				</c:otherwise>
    			</c:choose>	
				
				var startingTrainDate = $("#" + trainTable + " tr:eq(" + index + ") td:eq(4)").html();
				date = date + ' ' + startingTrainDate.split(' ')[1];
				
				$("#" + trainId).val($("#" + trainTable + " tr:eq(" + index + ") td:eq(0)").html());
				$("#" + time).val(date);
				// Set parameters to modal window
				$("#" + trainNo).val($("#" + trainTable + " tr:eq(" + index + ") td:eq(2)").html());
				// Show button
				$("#" + showTrains).show();
			}
		});
	}

	// Show or hide table rows and Show all trains button
	function showAllVariants(trainTable, trainId, time, showTrains, trainNo) {
		// Show all rows
		$("#" + trainTable + " tr").show();
		// Reset radio buttons
		$("#" + trainTable + " :input[type=radio]").prop('checked', false);
		// Clear values
		$("#" + trainId).val("");
		$("#" + time).val("");
		$("#" + trainNo).val("");
		// Hide button
		$("#" + showTrains).hide();
		
		// Change button state to purchase book ticket
		changeState();
	}

	// Add additional text to the modal view
	function setParameters() {
		var trainNo = $("#trainNoForward").val();
		$("#bookTicketModal #trainsInfo").append("<label>Purchase ticket for the train number:" + trainNo + "</label>");
		if ($("#trainIdBack").val() != "") {
			var trainNoBack = $("#trainNoBack").val();
			$("#bookTicketModal #trainsInfo").append("<label>Purchase ticket for the back train number:" + trainNoBack + "</label>");
		}
	}

	// Remove additional text from modal view
	function clearText() {
		$("#bookTicketModal #trainsInfo label").remove();
	}
	
	// Can purchase ticket only when both trains were chosen
	function changeState() {
		if ($("#trainIdBack").val() != "") {
			if ($("#forwardTrains :input[type=radio]").is(':checked') && $("#trainsBack :input[type=radio]").is(':checked')) {
				$("#purchaseTicket").removeAttr('disabled');
			}
			else if (!$("#forwardTrains :input[type=radio]").is(':checked') || !$("#trainsBack :input[type=radio]").is(':checked')) {
				$("#purchaseTicket").attr('disabled', 'disabled');
			}
		}
		else {
			if ($("#forwardTrains :input[type=radio]").is(':checked')) {
				$("#purchaseTicket").removeAttr('disabled');
			}
			else {
				$("#purchaseTicket").attr('disabled', 'disabled');
			}
		}
	}
	
	$(":input[type=radio]").change(changeState);
		
	// Configure validator	
	$.validator.setDefaults({
		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement: 'span',
		errorClass: 'help-block',
		errorPlacement: function(error, element) {
			if(element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
	
	// Add custom validator, that allows only letters to enter
	$.validator.addMethod("lettersonly", function(value, element) {
		return this.optional(element) || /^[a-zA-Z]+$/i.test(value);
	}, "Letters only please"); 

	// Set validator to form
	$("#bookTicketForm").validate({
	    rules: {
			firstName: {
				required: true,
				lettersonly: true
			},
			
			lastName: {
				required: true,
				lettersonly: true
			},
		  
			birthdate: {
				required: true
			}
	    }
	});
	
</script>

</html>