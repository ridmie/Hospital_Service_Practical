
$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

//Save inserted and updated data
$(document).on("click", "#btnSave", function(event)
{
	
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateHospitalForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidHospitalIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
	{
		url : "HospitalsAPI",
		type : type,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			 onHospitalSaveComplete(response.responseText, status);
		}
	});
});
	
function onHospitalSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
	 
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divHospitalsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidHospitalIDSave").val("");
	$("#formHospital")[0].reset();
}


	//Delete hospital
	$(document).on("click", ".btnRemove", function(event)
			{
			 $.ajax(
			 {
			 url : "HospitalsAPI",
			 type : "DELETE",
			 data : "hospitalID=" + $(this).data("hospitalid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
			 onItemDeleteComplete(response.responseText, status);
			 }
			 });
			});
	
	function onItemDeleteComplete(response, status)
	{
		if (status == "success")
		{
			var resultSet = JSON.parse(response);
			if (resultSet.status.trim() == "success")
			{
				$("#alertSuccess").text("Successfully deleted.");
				$("#alertSuccess").show();
				
				$("#divHospitalsGrid").html(resultSet.data);
			} else if (resultSet.status.trim() == "error")
			{
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
		} else if (status == "error")
		{
			$("#alertError").text("Error while deleting.");
			$("#alertError").show();
		} else
		{
			$("#alertError").text("Unknown error while deleting..");
			$("#alertError").show();
		}
	}
	
	//Update hospital
	$(document).on("click", ".btnUpdate", function(event)
	{
		$("#hidHospitalIDSave").val($(this).closest("tr").find('#hidHospitalIDUpdate').val());
		$("#name").val($(this).closest("tr").find('td:eq(0)').text());
		$("#type").val($(this).closest("tr").find('td:eq(1)').text());
		$("#description").val($(this).closest("tr").find('td:eq(2)').text());
		$("#phoneNo").val($(this).closest("tr").find('td:eq(3)').text());
		$("#email").val($(this).closest("tr").find('td:eq(4)').text());
		$("#code").val($(this).closest("tr").find('td:eq(5)').text());
		$("#city").val($(this).closest("tr").find('td:eq(6)').text());
		$("#state").val($(this).closest("tr").find('td:eq(7)').text());
		$("#hospitalFee").val($(this).closest("tr").find('td:eq(8)').text());
	}); 
	

	//Validation
	function validateHospitalForm()
	{
		// CODE
		if ($("#name").val().trim() == "")
		{
			return "Insert Hospital Name.";
		}
		
		// NAME
		if ($("#type").val().trim() == "")
		{
			return "Insert Hopsital Type.";
		}
		
		// DESCRIPTION------------------------
		if ($("#description").val().trim() == "")
		{
			return "Insert Hospital Description.";
		}
		
		// PHONE NUMBER------------------------
		var phoneRegEx = /^\d{10}$/;
		var tenmPhone = $("#phoneNo").val().trim(); 
		
		if ($("#phoneNo").val().trim() == "")
		{
			return "Insert Phone Number.";
		}
		if(!phoneRegEx.test(tenmPhone)){
			return "Enter Valid phone number";
		}
		
		// EMAIL------------------------
		var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		var tmpEmail = $("#email").val().trim();
		
		if ($("#email").val().trim() == ""  )
		{
			return "Insert Email Address";
		}
		if(!emailPattern.test(tmpEmail)){
			return "Enter Valid email";
		}
		
		// CODE------------------------
		if ($("#code").val().trim() == "")
		{
			return "Insert Address Code.";
		}
		
		// CITY------------------------
		if ($("#city").val().trim() == "")
		{
			return "Insert City.";
		}
		
		// STATE------------------------
		if ($("#state").val().trim() == "")
		{
			return "Insert State.";
		}
		
		// HOSPITAL FEE-------------------------------
		if ($("#hospitalFee").val().trim() == "")
		{
			return "Insert Hospital Fee.";
		}
		
		// is numerical value
		var tmpPrice = $("#hospitalFee").val().trim();
		if (!$.isNumeric(tmpPrice))
		{
			return "Insert a numerical value for Hospital Fee.";
		}
		// convert to decimal price
		$("#hospitalFee").val(parseFloat(tmpPrice).toFixed(2));
		
		return true;
	}



