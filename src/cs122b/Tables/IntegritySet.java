package cs122b.Tables;

import cs122b.Models.*;
import cs122b.Utilities.ConnectionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by dinhho on 2/14/15.
 */
public class IntegritySet extends Table
{

    private static File htmlReportFile;
    private static String filePath = "/Users/derrickjchie/Desktop/report.html";
    private static BufferedWriter bufferWriter;

    public void createReportFile()
    {
        try
        {
            htmlReportFile = new File(filePath);
            bufferWriter = new BufferedWriter(new FileWriter(htmlReportFile));
            bufferWriter.write("<html><head><title>Part 2: Cleaning the FabFlix Database</title></head><body>");
            bufferWriter.write("<h1>Part 2: Cleaning the FabFlix Database</h1>");
            bufferWriter.write("<h2>Integrity Report</h2>");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void closeReportFile()
    {
        try
        {
            bufferWriter.write("</body></html>");
            bufferWriter.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Movie> getMoviesWithoutGenres()
    {
        String sql = "SELECT * FROM movies AS m LEFT JOIN genres_in_movies AS g ON g.movie_id = m.id WHERE g.genre_id is NULL";
        String title = "Movies without any genres.";
        ArrayList<Movie> movies = moviesQueryHelper(sql, false);
        writeMovieReport(title, movies);

        return movies;
    }

    public ArrayList<Star> getStarsWithoutInvalidBirthdays()
    {
        String sql = "SELECT * FROM stars WHERE dob > CURDATE() OR dob < '1900-1-10'";
        String title = "Birth date > today or year < ~1900.";
        ArrayList<Star> stars = starQueryHelper(sql, false);
        writeStarReport(title, stars);

        return stars;
    }
    public ArrayList<Star> getDuplicateStars()
    {
        String sql = "select *, count(*) as count from stars group by first_name, last_name, dob having count(*) > 1";
        String title = "Stars that are the same.";
        ArrayList<Star> stars = starQueryHelper(sql, true);
        writeStarReport(title, stars);

        return stars;
    }

    public ArrayList<Genre> getDuplicateGenres()
    {
        String sql = "select *, count(name) as count from genres group by name having count(name) > 1";
        String title = "Genres that are the same.";
        ArrayList<Genre> genres = genreQueryHelper(sql, true);
        writeGenreReport(title, genres);

        return genres;
    }

    public ArrayList<Movie> getDuplicateMovies()
    {
        String sql = "select *, count(*) as count from movies group by title, year having count(*) > 1";
        String title = "Movies that are the same.";
        ArrayList<Movie> movies = moviesQueryHelper(sql, true);
        writeMovieReport(title, movies);

        return movies;
    }

    public ArrayList<CreditCard> getExpiredCreditCards()
    {
        String sql = "SELECT * FROM creditcards WHERE expiration < CURDATE() AND id IN (SELECT cc_id FROM customers)";
        String title = "Expired customer credit card. Report expired credit cards only if they belong to existing customers.";
        ArrayList<CreditCard> creditCards = creditCardQueryHelper(sql);
        writeCreditCardReport(title, creditCards);

        return creditCards;
    }

    public ArrayList<Genre> getGenresWithoutMovies()
    {
        String sql = "SELECT * FROM genres AS g LEFT JOIN genres_in_movies AS m ON m.genre_id = g.id WHERE m.movie_id is NULL";
        String title = "Genres without any movies.";
        ArrayList<Genre> genres = genreQueryHelper(sql, false);
        writeGenreReport(title, genres);

        return genres;
    }

    public ArrayList<Customer> getCustomersWithInvalidEmail()
    {
        String sql = "SELECT * FROM customers WHERE email not LIKE '%@%'";
        String title = "Customer email has no @sign.";
        ArrayList<Customer> customers = customerQueryHelper(sql);
        writeCustomerReport(title, customers);

        return customers;
    }

    public ArrayList<Movie> getMoviesWithoutStars()
    {
        String sql = "SELECT * FROM movies AS m LEFT JOIN stars_in_movies AS s ON m.id = s.movie_id WHERE s.movie_id is NULL";
        String title = "Movies without any star.";
        ArrayList<Movie> movies = moviesQueryHelper(sql, false);
        writeMovieReport(title, movies);

        return movies;
    }

    public ArrayList<Star> getStarsWithoutMovies()
    {
        String sql = "SELECT * FROM stars AS s LEFT JOIN stars_in_movies AS m ON m.star_id = s.id WHERE m.star_id is NULL";
        String title = "Stars without any movie.";
        ArrayList<Star> stars = starQueryHelper(sql, false);
        writeStarReport(title, stars);

        return stars;
    }

    public ArrayList<Star> getStarsWithoutFirstOrLastName()
    {
        String sql = "SELECT * FROM stars WHERE first_name is NULL OR first_name = '' OR last_name is NULL OR last_name =''";
        String title = "Stars with no first name or last name.";
        ArrayList<Star> stars = starQueryHelper(sql, false);
        writeStarReport(title, stars);

        return stars;
    }

    public static ArrayList<CreditCard> creditCardQueryHelper(String sql) {
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

    public static ArrayList<Customer> customerQueryHelper(String sql) {
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

    public static ArrayList<Star> starQueryHelper(String sql, boolean withCount) {
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
                if (withCount) {
                    s.setCount(rs.getInt("count"));
                }
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

    public static ArrayList<Movie> moviesQueryHelper(String sql, boolean withCount) {
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
                if (withCount) {
                    m.setCount(rs.getInt("count"));
                }
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

    public static ArrayList<Genre> genreQueryHelper(String sql, boolean withCount) {
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
                if (withCount) {
                    g.setCount(rs.getInt("count"));
                }
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

    public static void writeMovieReport(String title, ArrayList<Movie> movies)
    {
        try
        {
            bufferWriter.write("<h3>" + title + "</h3>");
            bufferWriter.write("<table style='width: 100%; border: 1px solid black;'>");
            bufferWriter.write("<tr>"
                    + "<th style='text-align: left; border: 1px solid black'>row</th>"
                    + "<th style='text-align: left; border: 1px solid black'>id</th>"
                    + "<th style='text-align: left; border: 1px solid black'>title</th>"
                    + "<th style='text-align: left; border: 1px solid black'>year</th>"
                    + "<th style='text-align: left; border: 1px solid black'>director</th>"
                    + "<th style='text-align: left; border: 1px solid black'>banner_url</th>"
                    + "<th style='text-align: left; border: 1px solid black'>trailer_url</th>"
                    + "<th style='text-align: left; border: 1px solid black'>count</th>"
                    + "</tr>");

            for (int i = 0; i < movies.size(); i++)
            {
                Movie movie = movies.get(i);

                bufferWriter.write("<tr>"
                        + "<td style='border: 1px solid black;'>" + (i + 1) + "</td>"
                        + "<td style='border: 1px solid black;'>" + movie.getId() + "</td>"
                        + "<td style='border: 1px solid black;'>" + movie.getTitle() + "</td>"
                        + "<td style='border: 1px solid black;'>" + movie.getYear() + "</td>"
                        + "<td style='border: 1px solid black;'>" + movie.getDirector() + "</td>"
                        + "<td style='border: 1px solid black;'>" + movie.getBannerURL() + "</td>"
                        + "<td style='border: 1px solid black;'>" + movie.getTrailerURL() + "</td>"
                        + "<td style='border: 1px solid black;'>" + movie.getCount() + "</td>"
                        + "</tr>");
            }

            bufferWriter.write("</table>");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void writeStarReport(String title, ArrayList<Star> stars)
    {
        try
        {
            bufferWriter.write("<h3>" + title + "</h3>");
            bufferWriter.write("<table style='width: 100%; border: 1px solid black;'>");
            bufferWriter.write("<tr>"
                    + "<th style='text-align: left; border: 1px solid black'>row</th>"
                    + "<th style='text-align: left; border: 1px solid black'>id</th>"
                    + "<th style='text-align: left; border: 1px solid black'>first_name</th>"
                    + "<th style='text-align: left; border: 1px solid black'>last_name</th>"
                    + "<th style='text-align: left; border: 1px solid black'>dob</th>"
                    + "<th style='text-align: left; border: 1px solid black'>photo_url</th>"
                    + "<th style='text-align: left; border: 1px solid black'>count</th>"
                    + "</tr>");

            for (int i = 0; i < stars.size(); i++)
            {
                Star star = stars.get(i);

                bufferWriter.write("<tr>"
                        + "<td style='border: 1px solid black;'>" + (i + 1) + "</td>"
                        + "<td style='border: 1px solid black;'>" + star.getId() + "</td>"
                        + "<td style='border: 1px solid black;'>" + star.getFirstName() + "</td>"
                        + "<td style='border: 1px solid black;'>" + star.getLastName() + "</td>"
                        + "<td style='border: 1px solid black;'>" + star.getDateOfBirth() + "</td>"
                        + "<td style='border: 1px solid black;'>" + star.getPhotoUrl() + "</td>"
                        + "<td style='border: 1px solid black;'>" + star.getCount() + "</td>"
                        + "</tr>");
            }

            bufferWriter.write("</table>");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void writeGenreReport(String title, ArrayList<Genre> genres)
    {
        try
        {
            bufferWriter.write("<h3>" + title + "</h3>");
            bufferWriter.write("<table style='width: 100%; border: 1px solid black;'>");
            bufferWriter.write("<tr>"
                    + "<th style='text-align: left; border: 1px solid black'>row</th>"
                    + "<th style='text-align: left; border: 1px solid black'>id</th>"
                    + "<th style='text-align: left; border: 1px solid black'>name</th>"
                    + "<th style='text-align: left; border: 1px solid black'>count</th>"
                    + "</tr>");

            for (int i = 0; i < genres.size(); i++)
            {
                Genre genre = genres.get(i);

                bufferWriter.write("<tr>"
                        + "<td style='border: 1px solid black;'>" + (i + 1) + "</td>"
                        + "<td style='border: 1px solid black;'>" + genre.getId() + "</td>"
                        + "<td style='border: 1px solid black;'>" + genre.getName() + "</td>"
                        + "<td style='border: 1px solid black;'>" + genre.getCount() + "</td>"
                        + "</tr>");
            }

            bufferWriter.write("</table>");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void writeCreditCardReport(String title, ArrayList<CreditCard> creditCards)
    {
        try
        {
            bufferWriter.write("<h3>" + title + "</h3>");
            bufferWriter.write("<table style='width: 100%; border: 1px solid black;'>");
            bufferWriter.write("<tr>"
                    + "<th style='text-align: left; border: 1px solid black'>row</th>"
                    + "<th style='text-align: left; border: 1px solid black'>id</th>"
                    + "<th style='text-align: left; border: 1px solid black'>first_name</th>"
                    + "<th style='text-align: left; border: 1px solid black'>last_name</th>"
                    + "<th style='text-align: left; border: 1px solid black'>expiration</th>"
                    + "</tr>");

            for (int i = 0; i < creditCards.size(); i++)
            {
                CreditCard creditCard = creditCards.get(i);

                bufferWriter.write("<tr>"
                        + "<td style='border: 1px solid black;'>" + (i + 1) + "</td>"
                        + "<td style='border: 1px solid black;'>" + creditCard.getId() + "</td>"
                        + "<td style='border: 1px solid black;'>" + creditCard.getFirstName() + "</td>"
                        + "<td style='border: 1px solid black;'>" + creditCard.getLastName() + "</td>"
                        + "<td style='border: 1px solid black;'>" + creditCard.getExpiration() + "</td>"
                        + "</tr>");
            }

            bufferWriter.write("</table>");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void writeCustomerReport(String title, ArrayList<Customer> customers)
    {
        try
        {
            bufferWriter.write("<h3>" + title + "</h3>");
            bufferWriter.write("<table style='width: 100%; border: 1px solid black;'>");
            bufferWriter.write("<tr>"
                    + "<th style='text-align: left; border: 1px solid black'>row</th>"
                    + "<th style='text-align: left; border: 1px solid black'>id</th>"
                    + "<th style='text-align: left; border: 1px solid black'>first_name</th>"
                    + "<th style='text-align: left; border: 1px solid black'>last_name</th>"
                    + "<th style='text-align: left; border: 1px solid black'>cc_id</th>"
                    + "<th style='text-align: left; border: 1px solid black'>address</th>"
                    + "<th style='text-align: left; border: 1px solid black'>email</th>"
                    + "<th style='text-align: left; border: 1px solid black'>password</th>"
                    + "</tr>");

            for (int i = 0; i < customers.size(); i++)
            {
                Customer customer = customers.get(i);

                bufferWriter.write("<tr>"
                        + "<td style='border: 1px solid black;'>" + (i + 1) + "</td>"
                        + "<td style='border: 1px solid black;'>" + customer.getId() + "</td>"
                        + "<td style='border: 1px solid black;'>" + customer.getFirstName() + "</td>"
                        + "<td style='border: 1px solid black;'>" + customer.getLastName() + "</td>"
                        + "<td style='border: 1px solid black;'>" + customer.getCreditCardId() + "</td>"
                        + "<td style='border: 1px solid black;'>" + customer.getAddress() + "</td>"
                        + "<td style='border: 1px solid black;'>" + customer.getEmail() + "</td>"
                        + "<td style='border: 1px solid black;'>" + customer.getPassword() + "</td>"
                        + "</tr>");
            }

            bufferWriter.write("</table>");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
