package cs122b.Utilities;

import cs122b.Models.Employee;

/**
 * Created by dinhho on 2/11/15.
 */
public class SessionManager {
    private static Employee currentUser;
    private static SessionManager instance = null;

    public static SessionManager getInstance() {
        if (instance == null)
            instance = new SessionManager();
        return instance;
    }

    public static void setCurrentUser(Employee user) {
        currentUser = user;
    }

    // this can return null, gotta be careful
    public static Employee getCurrentUser() {
        return currentUser;
    }
}
