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

            String sportType = SyntaxValidator.validateString(args[0])
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid("the sport type")
                    .getResult();

            String sportDiscipline = SyntaxValidator.validateString(args[1])
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid("the sport discipline")
                    .getResult();

            return new OlympicSport(sportType, sportDiscipline);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}