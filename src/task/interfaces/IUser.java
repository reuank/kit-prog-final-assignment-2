package task.interfaces;

import task.userinterface.models.UserGroup;

public interface IUser {
    UserGroup getUserGroup();
    String getUsername();
    String getPassword();

    void setUserGroup(UserGroup userGroup);
    void setUsername(String username);
    void setPassword(String password);
}