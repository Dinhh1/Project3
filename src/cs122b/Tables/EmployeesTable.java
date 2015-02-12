package cs122b.Tables;


import cs122b.Models.Employee;
import cs122b.Utilities.ConnectionManager;
import cs122b.Utilities.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dinhho on 1/14/15.
 */
public class EmployeesTable extends Table {

    public Employee authenticateEmployee(String username, String password) {
        Employee query = null;
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password))
            return query;
        String sql = "SELECT * FROM employees where email = ? and password = ?";
        Connection con = null; ConnectionManager.getConnection();
        PreparedStatement queryStatement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            queryStatement = con.prepareStatement(sql);
            queryStatement.setString(1, username);
            queryStatement.setString(2, password);
            rs = queryStatement.executeQuery();
            if (rs.next()) {
                query = new Employee(rs.getString("email"), rs.getString("password"), rs.getString("fullname"));
                query.setPassword("****");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                queryStatement.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                System.out.print(ex.getMessage());
            }
        }
        return query;
    }

}
