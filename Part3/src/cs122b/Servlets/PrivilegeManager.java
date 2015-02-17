package cs122b.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs122b.DB.MovieDB;
import cs122b.Utilities.ConnectionManager;

public class PrivilegeManager extends HttpServlet {
	private static final long serialVersionUID = 2242093137806792131L;
	
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		//handle changing permissions for the current user
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("user");
		String query = null;
		
		if (request.getParameter("database-name1") != null) {
			query = databasePrivilegeUpdate(request);
		}
		else if (request.getParameter("database-name2") != null) {
			query = tablePrivilegeUpdate(request);
		}
		else if (request.getParameter("database-name3") != null) {
			query = columnPrivilegeUpdate(request);
		}
		else if (request.getParameter("database-name4") != null) {
			query = storeProcedurePrivilegeUpdate(request);
		}
		else {
			response.sendRedirect("PrivilegeManager.jsp");
		}
		
		System.out.println(query);
		MovieDB db = new MovieDB();
		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		int result;
		
		try {
			ps = con.prepareStatement(query);
			result = ps.executeUpdate();
			session.setAttribute("updated", 1);
			response.sendRedirect("UserManager.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("UserManager.jsp");
		}
		
	}
	
	private String databasePrivilegeUpdate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String query;
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").equals("on")) {
			query = "REVOKE ";
		}
		else {
			query = "GRANT ";
		}
		if (request.getParameter("ALL") != null && request.getParameter("ALL").equals("on")) {
			if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) 
				query += "ALL ON " + request.getParameter("database-name1") + " FROM " + session.getAttribute("user");
			else
				query += "ALL ON " + request.getParameter("database-name1") + " TO " + session.getAttribute("user");
			return query;
		}
		Enumeration<String> paramNames = request.getParameterNames();
		int first = 1;
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramValue.equals("on") && !paramName.equals("REVOKE")) {
				if (first == 1) {
					query += paramName;
					first = -1;
				}
				else
					query += ", " + paramName;
			}
		}
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) {
			query += " ON " + request.getParameter("database-name1") + " FROM " + session.getAttribute("user");
		}
		else {
			query += " ON " + request.getParameter("database-name1") + " TO " + session.getAttribute("user");
		}
		
		return query;
	}
	
	private String tablePrivilegeUpdate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String query;
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) {
			query = "REVOKE ";
		}
		else {
			query = "GRANT ";
		}
		Enumeration<String> paramNames = request.getParameterNames();
		int first = 1;
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramValue.equals("on") && !paramName.equals("REVOKE")) {
				if (first == 1) {
					query += paramName;
					first = -1;
				}
				else
					query += ", " + paramName;
			}
		}
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) {
			query += " ON " + request.getParameter("database-name2") + "." + request.getParameter("table-name") + " FROM " + session.getAttribute("user");
		}
		else {
			query += " ON " + request.getParameter("database-name2") + "." + request.getParameter("table-name") + " TO " + session.getAttribute("user");
		}
		return query;
	}

	private String columnPrivilegeUpdate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String query;
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) {
			query = "REVOKE ";
		}
		else {
			query = "GRANT ";
		}
		Enumeration<String> paramNames = request.getParameterNames();
		int first = 1;
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramValue.equals("on") && !paramName.equals("REVOKE")) {
				if (first == 1) {
					query += paramName + "(" + request.getParameter("column-name") + ")";
					first = -1;
				}
				else
					query += ", " + paramName + "(" + request.getParameter("column-name") + ")";
			}
		}
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) {
			query += " ON " + request.getParameter("database-name3") + "." + request.getParameter("table-name") + " FROM " + session.getAttribute("user");

		}
		else {
			query += " ON " + request.getParameter("database-name3") + "." + request.getParameter("table-name") + " TO " + session.getAttribute("user");
		}
		return query;
	}

	private String storeProcedurePrivilegeUpdate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String query;
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) {
			query = "REVOKE ";
		}
		else {
			query = "GRANT ";
		}
		Enumeration<String> paramNames = request.getParameterNames();
		int first = 1;
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			if (paramValue.equals("on") && !paramName.equals("REVOKE")) {
				if (first == 1) {
					query += paramName;
					first = -1;
				}
				else
					query += ", " + paramName;
			}
		}
		if (request.getParameter("REVOKE") != null && request.getParameter("REVOKE").toString().equals("on")) {
			query += " ON " + request.getParameter("database-name4") + "." + request.getParameter("stored-procedure-name") + " FROM " + session.getAttribute("user");
		}
		else {
			query += " ON " + request.getParameter("database-name4") + "." + request.getParameter("stored-procedure-name") + " TO " + session.getAttribute("user");
		}
		
		return query;
	}
}
