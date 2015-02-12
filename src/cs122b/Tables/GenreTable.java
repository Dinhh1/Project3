package cs122b.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import cs122b.DB.MovieDB;
import cs122b.Models.*;
import cs122b.Utilities.ConnectionManager;

public class GenreTable extends Table {

	public GenreTable() {super();}
	
	public ArrayList<Genre> get() {
		ArrayList<Genre> query = new ArrayList<Genre>();
        Statement pS = null;
        ResultSet rs = null;
        Connection con = null;
        String sql = "select * from genres";
        try {
			con = ConnectionManager.getConnection();
			pS = con.createStatement();
            rs = pS.executeQuery(sql);
            while (rs.next()) {
            	Genre g = new Genre(rs.getInt("id"), rs.getString("name"));
//                g.getModelStatus().setStatusCode(ModelStatus.StatusCode.OK, true);
                query.add(g);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                pS.close();
                rs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return query;
	}
	
	public ArrayList<Genre> get(Movie m) {
		ArrayList<Genre> query = new ArrayList<Genre>();
		if (m == null || m.getId() == MovieDB.DBConstant.INVALID_ID)
			return query;
		PreparedStatement pS = null;
		ResultSet rs = null;
		Connection con = null;
		String sql = "select * from genres where id in (select genre_id from genres_in_movies where movie_id = ?)";
		try {
			con = ConnectionManager.getConnection();
			pS = con.prepareStatement(sql);
			pS.setInt(1, m.getId());
			rs = pS.executeQuery();
			while (rs.next()) {
            	Genre g = new Genre(rs.getInt("id"), rs.getString("name"));
//                g.getModelStatus().setStatusCode(ModelStatus.StatusCode.OK, true);
                query.add(g);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pS.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return query;
	}
	
	public Genre get(int id) {
		Genre g = null;
		PreparedStatement pS = null;
        ResultSet rs = null;
        Connection con = null;
        String sql = "select * from genres where id = ?";
        try {
			con = ConnectionManager.getConnection();
			pS = con.prepareStatement(sql);
			pS.setInt(1, id);
            rs = pS.executeQuery(sql);
            if (rs.next()) {
            	g = new Genre(rs.getInt("id"), rs.getString("name"));
//                g.getModelStatus().setStatusCode(ModelStatus.StatusCode.OK, true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                pS.close();
                rs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
		return g;
	}
	
	
	

}
