package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.olympia.models.SportType;
import task.validation.SyntaxValidator;

public class SportTypeParser implements IParser<SportType> {
    @Override
    public SportType parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(1)
                    .throwIfInvalid("the sport type data");

            String sportTypeName = args[0];

            SyntaxValidator.validateString(sportTypeName)
                    .isNotNull()
                    .throwIfInvalid("the sport type name");

            return new SportType(sportTypeName);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}