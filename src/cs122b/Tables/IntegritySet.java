package cs122b.Tables;

import com.sun.tools.javac.jvm.Gen;
import cs122b.DB.MovieDB;
import cs122b.Models.*;
import cs122b.Utilities.ConnectionManager;
import javafx.scene.control.Tab;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by dinhho on 2/14/15.
 */
public class IntegritySet extends Table {

    public ArrayList<Movie> getMoviesWithoutGenres() {
        String sql = "SELECT * FROM movies AS m LEFT JOIN genres_in_movies AS g ON g.movie_id = m.id WHERE g.genre_id is NULL";
        return moviesQueryHelper(sql);
    }

    public ArrayList<Movie> getDuplicateMovies() {
        String sql = "select *, count(*) as count from movies group by title, year having count(*) > 1";
        ArrayList<Movie> result = new ArrayList<Movie>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Movie m = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"), rs.getString("banner_url"),
                        rs.getString("trailer_url"), rs.getInt("count"));
                result.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }

    public ArrayList<Star> getStarsWithoutInvalidBirthdays() {
        String sql = "SELECT * FROM stars WHERE dob > CURDATE() OR dob < '1900-1-10'";
        return starQueryHelper(sql);
    }
    public ArrayList<Star> getDuplicateStars() {
        String sql = "select *, count(*) as count from stars group by first_name, last_name, dob having count(*) > 1";
        ArrayList<Star> result = new ArrayList<Star>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Star s = new Star(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("dob"), rs.getString("photo_url"), rs.getInt("count"));
                result.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;

    }
    public ArrayList<Genre> getDuplicateGenres() {
        String sql = "select *, count(name) as count from genres group by name having count(name) > 1";
        ArrayList<Genre> result = new ArrayList<Genre>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Genre g = new Genre(rs.getInt("id"), rs.getString("name"), rs.getInt("count"));
                result.add(g);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }


    public ArrayList<CreditCard> getExpiredCreditCards() {
        String sql = "SELECT * FROM creditcards WHERE expiration < CURDATE() AND id IN (SELECT cc_id FROM customers)";
        ArrayList<CreditCard> result = new ArrayList<CreditCard>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                CreditCard cc = new CreditCard(rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("expiration"));
                result.add(cc);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }

    public ArrayList<Genre> getGenresWithoutMovies() {
        String sql = "SELECT * FROM genres AS g LEFT JOIN genres_in_movies AS m ON m.genre_id = g.id WHERE m.movie_id is NULL";
        ArrayList<Genre> result = new ArrayList<Genre>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Genre g = new Genre(rs.getInt("id"), rs.getString("name"));
                result.add(g);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }

    public ArrayList<Customer> getCustomersWithInvalidEmail() {
        String sql = "SELECT * FROM customers WHERE email not LIKE '%@%'";
        ArrayList<Customer> result = new ArrayList<Customer>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Customer c = new Customer(rs.getString("first_name"), rs.getString("last_name"), rs.getString("cc_id"), rs.getString("address"), rs.getString("email"), rs.getString("password"));
                c.setPassword("************");
                result.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }

    public ArrayList<Movie> getMoviesWithoutStars() {
        String sql = "SELECT * FROM movies AS m LEFT JOIN stars_in_movies AS s ON m.id = s.movie_id WHERE s.movie_id is NULL";
        return moviesQueryHelper(sql);
    }

    public ArrayList<Star> getStarsWithoutMovies() {
        String sql = "SELECT * FROM stars AS s LEFT JOIN stars_in_movies AS m ON m.star_id = s.id WHERE m.star_id is NULL";
        return starQueryHelper(sql);
    }

    public ArrayList<Star> getStarsWithoutFirstOrLastName() {
        String sql = "SELECT * FROM stars WHERE first_name is NULL OR first_name = '' OR last_name is NULL OR last_name =''";
        return starQueryHelper(sql);
    }

    public static ArrayList<Star> starQueryHelper(String sql) {
        ArrayList<Star> result = new ArrayList<Star>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Star s = new Star(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("dob"), rs.getString("photo_url"));
                result.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }

    public static ArrayList<Movie> moviesQueryHelper(String sql) {
        ArrayList<Movie> result = new ArrayList<Movie>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Movie m = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"), rs.getString("banner_url"),
                        rs.getString("trailer_url"));
                result.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                statement.close();
                con.close();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }


}
