package task.userinterface.models;

import task.constructs.database.Model;
import task.exceptions.ValidationException;
import task.interfaces.IUser;
import task.validation.SyntaxValidator;

public class User extends Model implements IUser {
    private UserGroup userGroup;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    public User(UserGroup userGroup, String... args) throws ValidationException {
        SyntaxValidator.validateArray(args)
                .isNotEmpty()
                .isOfLength(4)
                .throwIfInvalid("the user data");

        String firstname = args[0];
        String lastname = args[1];
        String username = args[2];
        String password = args[3];

        SyntaxValidator.validateString(firstname)
                .isNotNull()
                .isNotContaining(";")
                .throwIfInvalid("firstname");

        SyntaxValidator.validateString(lastname)
                .isNotNull()
                .isNotContaining(";")
                .throwIfInvalid("lastname");

        SyntaxValidator.validateString(username)
                .isNotNull()
                .isOfLengthBetween(4, 8)
                .isNotContaining(";")
                .throwIfInvalid("username");

        SyntaxValidator.validateString(password)
                .isNotNull()
                .isOfLengthBetween(8, 12)
                .throwIfInvalid("password");

        this.userGroup = userGroup;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
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

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
