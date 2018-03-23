package task.userinterface.models;

import task.constructs.database.Model;

public class User extends Model {
    private UserGroup userGroup;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    public User(UserGroup userGroup, String... args) {
        this.userGroup = userGroup;
        this.firstname = args[0];
        this.lastname = args[1];
        this.username = args[2];
        this.password = args[3];
    }

    @Override
    public boolean equals(Object other) {
        return ((User) other).getUsername().equals(this.username);
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
