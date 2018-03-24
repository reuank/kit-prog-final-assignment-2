package task.userinterface.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.lang.Message;
import task.userinterface.models.User;
import task.userinterface.auth.UserGroup;
import task.validation.SyntaxValidator;

import static task.lang.Message.*;

/**
 * The user parser.
 */
public class UserParser implements IParser {
    @Override
    public User parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid(Message.get(USER) + " " + Message.get(DATA));

            String firstname = args[0];
            String lastname = args[1];
            String username = args[2];
            String password = args[3];

            SyntaxValidator.validateString(firstname)
                    .isNotNull()
                    .isNotContaining(";")
                    .throwIfInvalid(Message.get(FIRSTNAME));

            SyntaxValidator.validateString(lastname)
                    .isNotNull()
                    .isNotContaining(";")
                    .throwIfInvalid(Message.get(LASTNAME));

            SyntaxValidator.validateString(username)
                    .isNotNull()
                    .isOfLengthBetween(4, 8)
                    .isNotContaining(";")
                    .throwIfInvalid(Message.get(USERNAME));

            SyntaxValidator.validateString(password)
                    .isNotNull()
                    .isOfLengthBetween(8, 12)
                    .throwIfInvalid(Message.get(PASSWORD));

            return new User(UserGroup.getDefault(), firstname, lastname, username, password);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}
