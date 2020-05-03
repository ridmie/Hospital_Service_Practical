/**
 * 
 */
package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * @author Admin
 *
 */
public class Hospital {
	public Connection connect() {
		Connection con = null;

		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospitaldb?serverTimezone=UTC", "root", "");
			
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		return con;
	}

	//For Read Hospitals from Database
	public String readHospitals() 
	{
			String output= "";
			
			try {
					Connection con= connect();
				
					if(con==null)
					{
						return "Error while connecting to the database";
					}
				
					//prepare the html table to be displayed
					output = "<table border=\'1\'><tr><th>Hospital Name</th><th>Hospital Type</th><th>Description</th><th>Phone Number</th><th>Email</th>"
							+ "<th>Code</th><th>City</th><th>State</th><th>Hoapital Fee</th><th>Update</th><th>Remove</th></tr>";
				
					String query = "select * from hospital";
					Statement stmt= con.createStatement();
					ResultSet rs= stmt.executeQuery(query);
					
					//iterate through the rows in the result set
					while(rs.next())
					{
						String hospitalID = Integer.toString(rs.getInt("hospitalID"));
						String name = rs.getString("name");
						String type = rs.getString("type");
						String description = rs.getString("description");
						String phoneNo = rs.getString("phoneNo");
						String email = rs.getString("email");
						String code = rs.getString("code");
						String city = rs.getString("city");
						String state = rs.getString("state");
						String hospitalFee = Double.toString(rs.getDouble("hospitalFee"));
						
						
						//Add into the html table
						output += "<tr><td><input id='hidHospitalIDUpdate' name='hidHospitalIDUpdate' type='hidden' value='" + hospitalID
								+ "'>" + name + "</td>"; 
						output += "<td>" + type + "</td>";
						output += "<td>" + description + "</td>";
						output += "<td>" + phoneNo + "</td>";
						output += "<td>" + email + "</td>";
						output += "<td>" + code + "</td>";
						output += "<td>" + city + "</td>";
						output += "<td>" + state + "</td>";
						output += "<td>" + hospitalFee + "</td>";
						
						
						//buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td> "
								+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-hospitalid='"
								+ hospitalID + "'>" + "</td></tr>";
	
					}
					
					con.close();
					
					//complete the html table
					output+= "</table>";
					
				}catch(Exception e) {
					output = "Error while reading the items.";
					System.err.println(e.getMessage());
				}
				return output;
		}
		
	//For Insert Hospital Details
	public String insertHospital(String name, String type, String description, String phoneNo, String email, String code, String city, String state, String hospitalFee) 
	{
			
			String output = "";
			
			try
			{
				Connection con= connect();
				
				if(con == null)
				{
					return "Error while connecting to the database";
				}
				
				//create a prepared statement
				String query = " insert into hospital (`hospitalID`,`name`,`type`,`description`,`phoneNo`,`email`,`code`,`city`,`state`,`hospitalFee`)"
								+ "values(?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement preparedStmt =  con.prepareStatement(query);
				
				//binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, type);
				preparedStmt.setString(4,description);
				preparedStmt.setString(5,phoneNo);
				preparedStmt.setString(6,email);
				preparedStmt.setString(7,code);
				preparedStmt.setString(8,city);
				preparedStmt.setString(9, state);
				preparedStmt.setDouble(10,Double.parseDouble(hospitalFee));
				
				//execute the statement
				preparedStmt.execute();
				con.close();
				
				String newItems = readHospitals();
				output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			}catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
				 
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
	//For Update Hospital Details
	public String updateHospital(String hospitalID,String name, String type, String description, String phoneNo, String email, String code, String city, String state, String hospitalFee)
		{
			
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				
				// create a prepared statement
				String query = "UPDATE hospital SET name=?,type=?,description=?,phoneNo=?,email=?,code=?,city=?,state=?,hospitalFee=? WHERE hospitalID=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, name);
				preparedStmt.setString(2, type);
				preparedStmt.setString(3, description);
				preparedStmt.setString(4, phoneNo);
				preparedStmt.setString(5, email);
				preparedStmt.setString(6, code);
				preparedStmt.setString(7, city);
				preparedStmt.setString(8, state);
				preparedStmt.setDouble(9, Double.parseDouble(hospitalFee));
			
				preparedStmt.setInt(10, Integer.parseInt(hospitalID));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newItems = readHospitals();
				output = "{\"status\":\"success\", \"data\": \"" +
				newItems + "\"}";
			
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
				 
				System.err.println(e.getMessage());
			}
			
			return output;
		}


	//For Delete Hospital Details
	public String deleteHospital(String hospitalID) {
		String output = "";
		
		try {
			Connection con= connect();
			if(con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			//create a prepared statement
			String query = "delete from hospital where hospitalID=?";
			
			PreparedStatement preparedStmt= con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, Integer.parseInt(hospitalID));
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + 
			newItems + "\"}";
			
		}catch(Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
