package com;
import com.Hospital;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class HospitalsAPI
 */
@WebServlet("/HospitalsAPI")
public class HospitalsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Hospital hospObj=new Hospital();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HospitalsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String output = hospObj.insertHospital(request.getParameter("name"),
				request.getParameter("type"),
				request.getParameter("description"),
				request.getParameter("phoneNo"),
				request.getParameter("email"),
				request.getParameter("code"),
				request.getParameter("city"),
				request.getParameter("state"),
				request.getParameter("hospitalFee"));
		
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = hospObj.updateHospital(paras.get("hidHospitalIDSave").toString(),
				paras.get("name").toString().replace('+',' '),
				paras.get("type").toString().replace('+',' '),
				paras.get("description").toString().replace('+',' '),
				paras.get("phoneNo").toString(),
				paras.get("email").toString().replace('%','@'),
				paras.get("code").toString().replace("%2F","/"),
				paras.get("city").toString().replace('+',' '),
				paras.get("state").toString().replace('+',' '),
				paras.get("hospitalFee").toString());
		
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = hospObj.deleteHospital(paras.get("hospitalID").toString());
		response.getWriter().write(output);
	}
	
	private static Map getParasMap(HttpServletRequest request)
	{
	Map<String, String> map = new HashMap<String, String>();
	try
	{
	Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
	String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
	scanner.close();
	
	String[] params = queryString.split("&");
	
	for (String param : params)
	{
		String[] p = param.split("=");
		map.put(p[0], p[1]);
		}
		
	}catch (Exception e)
	{
	}
		
	return map;
		
	}

}
