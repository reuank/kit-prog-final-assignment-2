package task.userinterface.auth;

import task.constructs.database.Database;
import task.userinterface.models.User;

import java.util.ArrayList;

public class LDAP {
    private Database userDatabase;

    public LDAP(Database userDatabase) {
        this.userDatabase = userDatabase;
    }

    // Gets a username / Password combo and returns true if this combination is
    public boolean checkCredentials(String username, String password) {
        ArrayList<User> allUsers = this.userDatabase.getTable(User.class).getRows();
        return allUsers.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }
}