package cs122b.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs122b.DB.MovieDB;
import cs122b.Utilities.ConnectionManager;

public class UserManager extends HttpServlet {
	private static final long serialVersionUID = -9193707166819868065L;

	public void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		try {
			MovieDB db = new MovieDB();
			String query = "show grants for ?@?";
			String username = request.getParameter("usernameExists");
			
			Connection con = ConnectionManager.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, "localhost");
			rs = ps.executeQuery();
			//System.out.println(ps.toString());
			/*while (rs.next()) {
				System.out.println(rs.getString(1));
			}*/
			HttpSession session = request.getSession();
			session.setAttribute("user", "\'" + username + "\'@\'localhost\'");
			session.setAttribute("grants", rs);
			response.sendRedirect("PrivilegeManager.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("UserManager.jsp");
		}
	}
	
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		try {
			MovieDB db = new MovieDB();
			String query = "create user ?@\'localhost\' identified by ?";
			String username = request.getParameter("usernameNew");
			String password = request.getParameter("password");
			
			Connection con = ConnectionManager.getConnection();
			PreparedStatement ps = null;
			int result;
			
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			result = ps.executeUpdate();
			
			response.sendRedirect("UserManager.jsp");
		} catch (SQLException e) {
			request.getSession().setAttribute("success", -1);
			response.sendRedirect("UserManager.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("UserManager.jsp");
		}
	}

}
