<%@ page import = "com.Hospital" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Hospital Service</title>
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.4.1.min.js"></script>
		<script src="Components/hospitals.js"></script>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-6">
					<h1>Hospital Management</h1>
					<form id="formHospital" name="formHospital" >
	 					Hospital Name:
	 					<input id="name" name="name" type="text" class="form-control form-control-sm"> <br> 
	 					Hospital Type:
	 					<input id="type" name="type" type="text" class="form-control form-control-sm"> <br> 
	 					Description:
	 					<input id="description" name="description" type="text" class="form-control form-control-sm"> <br> 
	 					Phone Number:
	 					<input id="phoneNo" name="phoneNo" type="text" class="form-control form-control-sm"> <br>
	 					Email Address:
	 					<input id="email" name="email" type="text" class="form-control form-control-sm"> <br>
	 					Address Code:
	 					<input id="code" name="code" type="text" class="form-control form-control-sm"> <br>
	 					City:
	 					<input id="city" name="city" type="text" class="form-control form-control-sm"> <br>
	 					State:
	 					<input id="state" name="state" type="text" class="form-control form-control-sm"> <br>
	 					Hospital Fee:
	 					<input id="hospitalFee" name="hospitalFee" type="text" class="form-control form-control-sm"> <br>
	 					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
	 					<input type="hidden" id="hidHospitalIDSave" name="hidHospitalIDSave" value="">
					</form>
					
					
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
						<div id="divHospitalsGrid">
							 <%
							 Hospital hospObj = new Hospital();
							 out.print(hospObj.readItems());
							 %>
						</div>
				</div>
 			</div>
		</div>
	</body>
</html>