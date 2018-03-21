package task.userinterface.auth;

import task.constructs.database.Database;
import task.exceptions.AuthException;
import task.lang.Message;
import task.userinterface.models.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static task.lang.Message.*;

public class AccountManager {
    private Database userDatabase;
    private Session session;
    private LDAP ldap;

    public AccountManager(Database userDatabase, Session session) {
        this.userDatabase = userDatabase;
        this.session = session;
        this.ldap = new LDAP(this.userDatabase);
    }

    public void tryLogin(String username, String password) throws AuthException {
        if (!ldap.checkCredentials(username, password)) {
            throw new AuthException(Message.get(WRONG_CREDENTIALS));
        }

        this.session.start(getUser(username));
    }

    public void logout() {
        this.session.reset();
    }

    public boolean tryRegister(User newUser) throws AuthException {
        if (this.userExists(newUser.getUsername())) {
            throw new AuthException(Message.get(THIS_$STRING$_ALREADY_EXISTS, "username"));
        }

        this.userDatabase.getTable(User.class).insert(newUser);
        return true;
    }

    public boolean userExists(String username) {
        ArrayList<User> allUsers = this.userDatabase.getTable(User.class).getRows();
        return allUsers.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    private User getUser(String username) {
        ArrayList<User> allUsers = this.userDatabase.getTable(User.class).getRows();
        return allUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .collect(Collectors.toList())
                .get(0);
    }
}
