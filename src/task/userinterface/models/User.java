package task.userinterface.models;

import task.constructs.database.Model;

/**
 * The user model.
 */
public class User extends Model {
    private UserGroup userGroup;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    /**
     * Instantiates a new User.
     * @param userGroup the user group the user shall have.
     * @param args the args assigned to the user.
     */
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

    /**
     * @return - returns the userGroup
     **/
    public UserGroup getUserGroup() {
        return userGroup;
    }

    /**
     * Sets the userGroup
     *
     * @param userGroup - the new userGroup
     **/
    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * @return - returns the firstname
     **/
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname
     *
     * @param firstname - the new firstname
     **/
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return - returns the lastname
     **/
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname
     *
     * @param lastname - the new lastname
     **/
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return - returns the username
     **/
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     *
     * @param username - the new username
     **/
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return - returns the password
     **/
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     *
     * @param password - the new password
     **/
    public void setPassword(String password) {
        this.password = password;
    }
}
