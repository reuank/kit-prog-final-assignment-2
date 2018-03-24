package task.userinterface.auth;

import task.constructs.database.Database;
import task.userinterface.models.User;

import java.util.ArrayList;

/**
 * This class simply checks whether a given username-password combination is in the database.
 * This can be compared to the way LDAP works, hence the name.
 */
public class LDAP {
    private Database userDatabase;

    /**
     * Instantiates a new LDAP-checker
     * @param userDatabase the user database
     */
    public LDAP(Database userDatabase) {
        this.userDatabase = userDatabase;
    }

    /**
     * Checks whether a username / password combination is in the database.
     *
     * @param username the username.
     * @param password the password.
     * @return - returns true if the combination was found.
     */
    public boolean checkCredentials(String username, String password) {
        ArrayList<User> allUsers = this.userDatabase.getTable(User.class).getRows();
        return allUsers.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }
}