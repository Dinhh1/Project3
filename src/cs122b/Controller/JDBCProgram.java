package cs122b.Controller;

import cs122b.DB.*;
import cs122b.Models.*;
import cs122b.Utilities.ConnectionManager;
import cs122b.Utilities.SessionManager;
import cs122b.Utilities.StringUtils;


import java.sql.*;
import java.sql.Date;
import java.util.*;


/**
 * Created by dinhho on 1/10/15.
 */
public class JDBCProgram {

    private MovieDB db;

    public JDBCProgram() {
        ConnectionManager.loadDataSource("test", "1234", false);
    }

    public void start() {
        this.displayWelcomeMsg();
        Scanner in = new Scanner(System.in);
        boolean connected = false;
        this.db = new MovieDB();
        while (!connected) {
            System.out.print("Login: ");
            String userName = in.next();
            System.out.print("Password: ");
            String password = in.next();
            try {
                Employee e = db.Employees.authenticateEmployee(userName, password);
                if (e != null) {
                    connected = true;
                    SessionManager.getInstance().setCurrentUser(e);
                } else {
                    System.out.println("INVALID CREDENTIALS, please try again");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
        System.out.println("LOGIN SUCCESS!");
        System.out.println();
        System.out.println("************************************************************************************************************");
        System.out.println("                                    Welcome to FabFlix Management Console                                   ");
        System.out.println("************************************************************************************************************");
        this.mainLoop();
    }

    private void mainLoop() {
        boolean toQuit = false;
        boolean logout = false;
        int choice = 0;
        Scanner in = new Scanner(System.in);
        while (!toQuit && !logout) {
            this.displayMenu();

            System.out.print("Enter Choice: ");
            try {
                choice = in.nextInt();
            } catch (InputMismatchException e) {
                in.next();
                choice = 0;
            }
            switch (choice) {
                case 1:
                    this.queryMovieProcedure();
                    break;
                case 2:
                    this.insertNewStarProcedure();
                    break;
                case 3:
                    this.insertNewCustomerProcedure();
                    break;
                case 4:
                    this.deleteCustomerProcedure();
                    break;
                case 5:
                    this.displayDBMetaDataProcedure();
                    break;
                case 6:
                    this.customQueryProcedure();
                    break;
                case 7:
                    this.addNewMovieProcedure();
                    break;
                case 8:
                    this.generateDBIntegrityReport();
                    break;
                case 9:
                    logout = this.logoutProcedure();
                    break;
                case 10:
                    toQuit = this.quitProcedure();
                    break;
                default:
                    System.out.println("Invalid Choice, Please try again");
                    System.out.println();
                    break;
            }
        }
        if (logout) {
            this.start();
        }
    }

    private void displayMenu() {
        System.out.println("FabFlix Console Menu");
        System.out.println("1 - Query Movies featuring star");
        System.out.println("2 - Insert new star");
        System.out.println("3 - Insert new customer");
        System.out.println("4 - Delete a customer");
        System.out.println("5 - Display DB metadata");
        System.out.println("6 - Execute Custom Query");
        System.out.println("7 - Add Movie ");
        System.out.println("8 - Check Database Integrity");
        System.out.println("9 - Logout");
        System.out.println("10 - Quit Application");
    }
    private <T> void printDBSet(ArrayList<T> set) {
        for (T obj: set) {
            System.out.println(obj.toString());
        }
    }

    private void generateDBIntegrityReport() {

        System.out.println("Generating DB Integrity Report");

        System.out.println("Looking for movies without stars...");
        ArrayList<Movie> moviesWithoutStars = db.IntegritySet.getMoviesWithoutStars();
        this.printDBSet(moviesWithoutStars);

        System.out.println("Looking for stars without movies...");
        ArrayList<Star> starsWithoutMovies = db.IntegritySet.getStarsWithoutMovies();
        this.printDBSet(starsWithoutMovies);

        System.out.println("Looking for genres without movies...");
        ArrayList<Genre> genresWithoutMovies = db.IntegritySet.getGenresWithoutMovies();
        this.printDBSet(genresWithoutMovies);

        System.out.println("Looking for movies without genres...");
        ArrayList<Movie> moviesWithoutGenres = db.IntegritySet.getMoviesWithoutGenres();
        this.printDBSet(moviesWithoutGenres);

        System.out.println("Looking for stars without first or last name");
        ArrayList<Star> starsWithoutName = db.IntegritySet.getStarsWithoutFirstOrLastName();
        this.printDBSet(starsWithoutName);

        System.out.println("Looking expired credit cards with existing customers");
        ArrayList<CreditCard> expiredCreditCards = db.IntegritySet.getExpiredCreditCards();
        this.printDBSet(expiredCreditCards);

        // with the duplicate queries, i populated a field called count, and you can just
        // genre.getCount() to count the number of duplicates if you want to display that
        System.out.println("Looking for duplicate genres...");
        ArrayList<Genre> duplicateGenres = db.IntegritySet.getDuplicateGenres();
        this.printDBSet(duplicateGenres);

        System.out.println("Looking for duplicate movies...");
        ArrayList<Movie> duplicateMovies = db.IntegritySet.getDuplicateMovies();
        this.printDBSet(duplicateMovies);

        System.out.println("Looking for duplicate stars...");
        ArrayList<Star> duplicateStars = db.IntegritySet.getDuplicateStars();
        this.printDBSet(duplicateStars);

        System.out.println("Looking for stars with invalid birthdays...");
        ArrayList<Star> starsWithInvalidBirthdays = db.IntegritySet.getStarsWithoutInvalidBirthdays();
        this.printDBSet(starsWithInvalidBirthdays);

        System.out.println("Looking for customer's email without @ sign...");
        ArrayList<Customer> customersWithInvalidEmail = db.IntegritySet.getCustomersWithInvalidEmail();
        this.printDBSet(customersWithInvalidEmail);


    }

    private void addNewMovieProcedure() {
        Scanner in = new Scanner(System.in);
        System.out.println("Add New Movie");
        System.out.println("Enter movie title: ");
        String title = in.nextLine();
        System.out.println("Enter year: ");
        String year = in.nextLine();
        System.out.println("Enter Director: ");
        String director = in.nextLine();
        System.out.println("Enter banner url: ");
        String bannerUrl = "";
        String trailerUrl = "";
        bannerUrl = in.nextLine();
        System.out.println("Enter trailer url: ");
        trailerUrl = in.nextLine();
        System.out.println("Enter star's first name: ");
        String starsFname = in.nextLine();
        System.out.println("Enter star's last name: ");
        String starsLname = in.nextLine();
        System.out.println("Enter movie genre: ");
        String genre = in.nextLine();
        if (!StringUtils.isNullOrEmpty(title) && !StringUtils.isNullOrEmpty(year) &&
                !StringUtils.isNullOrEmpty(director) && !StringUtils.isNullOrEmpty(starsFname) &&
                !StringUtils.isNullOrEmpty(starsLname) && !StringUtils.isNullOrEmpty(genre)) {
            String output = db.Movies.addMovieWithSP(title, Integer.parseInt(year), director, bannerUrl,
                    trailerUrl, starsFname, starsLname, genre);
            System.out.println(output);
        }
    }

    /*
        Print out (to the screen) the movies featuring a given star. All movie attributes should appear, labeled and
        neatly arranged; the star can be queried via first name and/or last name or by ID. First name and/or last name
        means that a star should be queried by both a) first name AND last name b) first name or last name.
     */
    private void queryMovieProcedure() {
        ArrayList<Movie> query = null;
        System.out.println("Query movies by star");
        System.out.println("Movie Query Options: ");
        System.out.println("\t1. Query movie by star id: ");
        System.out.println("\t2. Query movie by star's first name");
        System.out.println("\t3. Query movie by star's last name");
        System.out.println("\t4. Query movie by star's first and last name");
        System.out.println("\t5. Return to main menu");
        Scanner reader = new Scanner(System.in);
        int choice = -1;
        while (true) {
            System.out.print("Enter query option: ");
            try {
                choice = reader.nextInt();
            } catch (InputMismatchException e) {
                reader.next();
            }
            if (choice == 1) {
                System.out.print("Enter star's id: ");
                int id = reader.nextInt();
                query = this.db.Movies.getMovieStarring(id);
                break;
            } else if (choice == 2) {
                System.out.print("Enter star's first name: ");
                String f = reader.next();
                query = this.db.Movies.getMovieStarring(f, null);
                break;
            } else if (choice == 3) {
                System.out.println("Enter star's last name: ");
                String l = reader.next();
                query = this.db.Movies.getMovieStarring(null, l);
                break;
            } else if (choice == 4) {
                System.out.println("Enter star's first name: ");
                String f = reader.next();
                System.out.println("Enter star's last name: ");
                String l = reader.next();
                query = this.db.Movies.getMovieStarring(f, l);
                break;
            } else if (choice == 5) {
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println("****************** QUERY RESULT **********************\n");
        if (query == null || query.size() == 0 )
            System.out.println("NO RESULT");
        else {
            for (Movie m: query) {
                System.out.println(m.toString() + "\n");
            }
        }
        System.out.println("******************* END ********************************\n");
    }

    /*
        Insert a new star into the database. If the star has a single name, add it as his last_name and assign an empty
        string ("") to first_name.
     */
    private void insertNewStarProcedure() {
        String insertString = "INSERT INTO stars(first_name, last_name, dob, photo_url) VALUES(?, ?, ?, ?)";
        String firstName = "";
        String lastName = "";
        Date dob = null;
        String photoUrl = "";

        Scanner in = new Scanner(System.in);

        System.out.println("Insert a star");
        System.out.println("Enter in the star's name:");

        String starName = in.nextLine();
        StringTokenizer st = new StringTokenizer(starName);
        if (st.countTokens() == 1)
        {
            lastName = st.nextToken();
        }
        else
        {
            // TODO: Need to handle the case when there's nothing entered.
            // TODO: Need to handle the case when there are more than three tokens. Eg. "Robert Downey Jr."
            firstName = st.nextToken();
            lastName = st.nextToken();
        }

        System.out.println("Enter in the star's date of birth (yyyy-mm-dd):");
        dob = Date.valueOf(in.nextLine());

        System.out.println("Enter in a url to the star's image:");
        photoUrl = in.nextLine();

        System.out.println("The first name is: " + firstName);
        System.out.println("The last name is: " + lastName);
        System.out.println("The date of birth is: " + dob.toString());
        System.out.println("The photo url is: " + photoUrl);

        int returnedValue = -1;
        returnedValue = this.db.Stars.addEntry(firstName, lastName, dob, photoUrl);
        System.out.println("The returned value is: " + returnedValue);

        if (returnedValue == 1)
        {
            System.out.println("Added a new star!");
        }
        else
        {
            System.out.println("Failed to add a new star!");
        }
    }

    /*
        Insert a customer into the database. Do not allow insertion of a customer if his credit card does not exist in
        the credit card table. The credit card table simulates the bank records. If the customer has a single name, add
        it as his last_name and and assign an empty string ("") to first_name.
     */
    private void insertNewCustomerProcedure() {
        System.out.println("Insert New Customer");
        System.out.println("Enter Customer's Full Name:");
        Scanner in = new Scanner(System.in);
        String customerName = in.nextLine();
        String firstName = ""; // this is the default firstName if the user only provides 1 name
        String lastName = "";
        String cc = null;
        Date expDate = null;
        String address = "";
        String email = "";
        String password = "";
        StringTokenizer st = new StringTokenizer(customerName);
        if (st.countTokens() == 1) {
            // case where customer has a single name
            lastName = st.nextToken();
        } else {
            firstName = st.nextToken();
            lastName = st.nextToken();
        }
        System.out.println("Enter address: ");
        address = in.nextLine();
        System.out.println("Enter email: ");
        email = in.nextLine();
        System.out.println("Enter password: ");
        password = in.nextLine();
        // need to check if customer has a credit card
        cc = this.customerHasCreditCard(firstName, lastName);
        int success = -1;
        // need to insert creditcard before inserting a customer if customer does not have a cc
        if (cc == null) {
            System.out.println("Enter Customer's Credit Card #");
            cc = in.nextLine();
            // keep asking for date until the user enters a valid date
            while (true) {
                System.out.println("Enter expiration date (yyyy-mm-dd): ");
                String expDateString = in.nextLine();
                try {
                    expDate = Date.valueOf(expDateString);
                    break;
                } catch(IllegalArgumentException e) {
                    System.out.println("Invalid input, please entry again");
                }
            }
//            success = this.db.addCreditCard(new CreditCard(cc, firstName,  lastName, expDate));
            success = this.db.CreditCards.addEntry(cc, firstName, lastName, expDate);
        } else
            success = 1;
        if (success == 1) {
            // now insert customer
            success = this.db.Customers.addEntry(new Customer(firstName, lastName, cc, address, email, password));
                if (success == 1)
                System.out.println(success + " new customer added!");
             else
                System.out.println("Fail to add customer");
        }
    }

    private String customerHasCreditCard(String fName, String lName) {
        String cc = null;
        ArrayList<CreditCard> query = this.db.CreditCards.get(fName, lName);
        if (query.size() > 0)
            cc = query.get(0).getId();
        return cc;
    }

    /*
        Delete a customer from the database.
     */
    private void deleteCustomerProcedure() {
        System.out.println("Delete Customer Menu");
        System.out.println("Please enter name of customer or customer id");
        Scanner in = new Scanner(System.in);
        String cName = in.nextLine();
        StringTokenizer st = new StringTokenizer(cName);
        int success = -1;
        if (st.countTokens() == 1) {
            String input = st.nextToken();
            try {
                // customer id case
                int cid = Integer.parseInt(input);
//                success = this.db.deleteCustomer(cid);
                success = this.db.Customers.deleteEntry(cid);
            } catch (NumberFormatException e) {
                // if the user only input 1 token and its not an int then we assume the user meant customer with
                // no first name and will query based on this assumption
//                success = this.db.deleteCustomer("", cName);
                success = this.db.Customers.deleteEntry("", cName);

            }
        } else {
            // case where the user provided first and last name
            String firstName = st.nextToken();
            String lastName = st.nextToken();
//            success = this.db.deleteCustomer(firstName, lastName);
            success = this.db.Customers.deleteEntry(firstName, lastName);
        }
        if (success == -1)
            System.out.println("Failed to delete");
        else
            System.out.println(success + " row(s) deleted" + "\n");
    }


    /*
        Provide the metadata of the database; in particular, print out the name of each table and, for each table,
        each attribute and its type.
     */
    private void displayDBMetaDataProcedure() {
        this.db.printDBMetaData();
    }

    /*
          Enter a valid SELECT/UPDATE/INSERT/DELETE SQL command. The system should take the corresponding action, and
           return and display the valid results. For a SELECT query, display the answers. For the other types of
           queries, give enough information about the status of the execution of the query. For instance, for an UPDATE
           query, show the user how many records have been successfully changed.
       */
    private void customQueryProcedure() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a valid SELECT/UPDATE/INSERT/DELETE/DELETE SQL Query:");
        String queryString = in.nextLine();
        StringTokenizer st = new StringTokenizer(queryString);
        System.out.println("************************ QUERY RESULT ********************************");
        if (st.hasMoreTokens()) {
            try {
                String command = st.nextToken().toLowerCase();
                if (command.equals("select")) {
                    ResultSet rs = this.db.executeQuery(queryString);
                    int rowCount = 0;
                    if (rs != null ) {
                        // get row count
                        rs.last();
                        rowCount = rs.getRow();
                        System.out.println("number of rows: " + rowCount);
                        rs.beforeFirst();
                        ResultSetMetaData md = rs.getMetaData();
                        int numberOfColumns = md.getColumnCount();
                        while (rs.next()) {
                            for (int i = 1; i < numberOfColumns; i++) {
                                String columnName = md.getColumnName(i);
                                System.out.println(columnName + " = " + rs.getString(i));
                            }
                            System.out.println();
                        }
                        rs.close();
                    }
                } else {
                    int success = this.db.executeUpdateQuery(queryString);
                    if (success != -1) {
                        System.out.println(success + " number of row(s) affected");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("******************************** END **************************************");
    }

    private boolean logoutProcedure() {
        System.out.println("Logging Out");
        System.out.println();
        return true;
    }

    private boolean quitProcedure() {
        System.out.println();
        System.out.println("Quiting Application");
        return true;
    }


    private void displayWelcomeMsg() {
        System.out.println("*******************************************************");
        System.out.println("*           FapFlix Information Management System     *");
        System.out.println("*******************************************************");
        System.out.println("Welcome to FIMS");
        System.out.println("Please Login");
    }
}
