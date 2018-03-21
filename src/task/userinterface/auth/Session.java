package task.userinterface.auth;

import task.userinterface.models.User;
import task.userinterface.models.UserGroup;

public class Session {
    User user;

    public Session() {

    }

    public Session(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void start(User user) {
        this.user = user;
    }

    public boolean exists() {
        return this.user != null;
    }

    public void reset() {
        this.user = null;
    }
}