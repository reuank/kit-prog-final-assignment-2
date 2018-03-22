package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.olympia.models.SportDiscipline;
import task.validation.SyntaxValidator;

public class SportDisciplineParser implements IParser<SportDiscipline> {
    @Override
    public SportDiscipline parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(1)
                    .throwIfInvalid("the sport discipline data");

            String sportDisciplineName = args[0];

            SyntaxValidator.validateString(sportDisciplineName)
                    .isNotNull()
                    .throwIfInvalid("the sport discipline name");

            return new SportDiscipline(sportDisciplineName);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}
