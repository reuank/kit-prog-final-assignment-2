package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.lang.Message;
import task.olympia.models.OlympicSport;
import task.validation.SyntaxValidator;

import static task.lang.Message.*;

public class OlympicSportParser implements IParser<OlympicSport> {
    @Override
    public OlympicSport parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(2)
                    .throwIfInvalid(Message.get(OLYMPIC_SPORT) + " " + Message.get(DATA));

            String sportType = SyntaxValidator.validateString(args[0])
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid(Message.get(SPORTS_TYPE))
                    .getResult();

            String sportDiscipline = SyntaxValidator.validateString(args[1])
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid(Message.get(SPORTS_DISCIPLINE))
                    .getResult();

            return new OlympicSport(sportType, sportDiscipline);
        } catch (ValidationException validationException) {
            throw new ParserException(Message.get(OLYMPIC_SPORT), validationException);
        }
    }
}