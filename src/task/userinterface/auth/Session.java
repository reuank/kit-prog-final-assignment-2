package task.userinterface.auth;

import task.interfaces.IUser;
import task.userinterface.models.UserGroup;

public class Session {
    IUser user;
    UserGroup group;

    public Session() {
        this.group = UserGroup.getDefault();
    }

    public Session(IUser user) {
        this.user = user;
        this.group = user.getUserGroup();
    }

    public IUser getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }

    public UserGroup getGroup() {
        return this.group;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public void reset() {
        this.user = null;
        this.group = UserGroup.getDefault();
    }
}