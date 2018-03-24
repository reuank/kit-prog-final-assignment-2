package task.userinterface.auth;

import task.userinterface.models.User;

/**
 * Used for storing and passing session data.
 * If a user logs in, a session is created and shared between all relevant classes.
 */
public class Session {
    private User user;

    /**
     * Instantiates a new session object.
     */
    public Session() {

    }

    /**
     * @return - returns the user
     **/
    public User getUser() {
        return user;
    }

    /**
     * Starts a new session.
     *
     * @param user the user the session belongs to.
     */
    public void start(User user) {
        this.user = user;
    }

    /**
     * @return - returns true if a session with a user is active.
     */
    public boolean exists() {
        return this.user != null;
    }

    /**
     * Resets the session by removing the user.
     */
    public void reset() {
        this.user = null;
    }
}