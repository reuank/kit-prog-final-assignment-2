package task.userinterface.auth;

import task.constructs.database.Database;
import task.exceptions.AuthException;
import task.lang.Message;
import task.userinterface.models.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static task.lang.Message.*;

/**
 * The account manager handles all the authentication / registration part
 */
public class AccountManager {
    private Database userDatabase;
    private Session session;
    private LDAP ldap;

    /**
     * Instantiates a new account manager.
     *
     * @param userDatabase the user database.
     * @param session the session object.
     */
    public AccountManager(Database userDatabase, Session session) {
        this.userDatabase = userDatabase;
        this.session = session;
        this.ldap = new LDAP(this.userDatabase);
    }

    /**
     * Tries to log a user in.
     *
     * @param username The username.
     * @param password His / Her password.
     * @throws AuthException thrown if the tryLogin process went wrong, so if the credentials weren't correct.
     */
    public void tryLogin(String username, String password) throws AuthException {
        if (!ldap.checkCredentials(username, password)) {
            throw new AuthException(Message.get(WRONG_CREDENTIALS));
        }

        this.session.start(getUser(username));
    }

    /**
     * Logs out a user, thus destroys the current session.
     */
    public void logout() {
        this.session.reset();
    }

    /**
     * Tries to register a new user.
     *
     * @param newUser The new user.
     * @return - returns true if everything went right.
     * @throws AuthException thrown if the user already exists.
     */
    public boolean tryRegister(User newUser) throws AuthException {
        if (this.userExists(newUser.getUsername())) {
            throw new AuthException(Message.get(THIS_$STRING$_ALREADY_EXISTS, USERNAME.get()));
        }

        this.userDatabase.getTable(User.class).insert(newUser);
        return true;
    }

    private boolean userExists(String username) {
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
