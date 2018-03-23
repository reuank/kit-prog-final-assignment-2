package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.olympia.models.OlympicSport;
import task.validation.SyntaxValidator;

public class OlympicSportParser implements IParser<OlympicSport> {
    @Override
    public OlympicSport parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(2)
                    .throwIfInvalid("the olympic sport data");

            String sportType = args[0];
            String sportDiscipline = args[1];

            SyntaxValidator.validateString(sportType)
                    .isNotNull()
                    .throwIfInvalid("sport type");

            SyntaxValidator.validateString(sportDiscipline)
                    .isNotNull()
                    .throwIfInvalid("sport discipline");

            return new OlympicSport(sportType, sportDiscipline);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}