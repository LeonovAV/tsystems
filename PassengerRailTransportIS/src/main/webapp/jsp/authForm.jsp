<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<form role="form" id="authForm" method="post">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Authorization</h4>
			</div>
				
			<div class="modal-body">
				<div class="row reset">
					<div class="form-group col-lg-12 rightpadding">
						<label for="login">Login:</label>
						<input type="text" class="form-control large-input" name="login" placeholder="Login">
					</div>
				</div>
				<div class="row reset">
					<div class="form-group col-lg-12 rightpadding">
						<label for="password">Password:</label>
						<input type="password" class="form-control large-input" name="password" placeholder="Password">
					</div>
				</div>
			</div>
				
			<div class="modal-footer">
				<input type="hidden" name="command" value="AuthWorker">
				<button type="submit" class="btn btn-default">Sign in</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
				
		</div>
	</form>
</body>
</html>