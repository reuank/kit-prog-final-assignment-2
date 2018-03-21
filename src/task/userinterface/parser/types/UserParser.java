package task.userinterface.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.userinterface.models.User;
import task.userinterface.models.UserGroup;
import task.validation.SyntaxValidator;

public class UserParser implements IParser {
    @Override
    public User parse(String... args) throws ParserException {
        try {
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
                    .throwIfInvalid("the firstname");

            SyntaxValidator.validateString(lastname)
                    .isNotNull()
                    .isNotContaining(";")
                    .throwIfInvalid("the lastname");

            SyntaxValidator.validateString(username)
                    .isNotNull()
                    .isOfLengthBetween(4, 8)
                    .isNotContaining(";")
                    .throwIfInvalid("the username");

            SyntaxValidator.validateString(password)
                    .isNotNull()
                    .isOfLengthBetween(8, 12)
                    .throwIfInvalid("the password");

            return new User(UserGroup.getDefault(), firstname, lastname, username, password);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}
