package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.lang.Message;
import task.olympia.models.Athlete;
import task.olympia.models.OlympicSport;
import task.validation.SyntaxValidator;

import static task.lang.Message.*;

public class AthleteParser implements IParser<Athlete> {
    @Override
    public Athlete parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(6)
                    .throwIfInvalid(Message.get(ATHLETE) + " " + Message.get(DATA));

            String athleteId = args[0];
            String firstname = args[1];
            String lastname = args[2];
            String countryName = args[3];
            String sportType = args[4];
            String sportDiscipline = args[5];

            SyntaxValidator.validateString(athleteId)
                    .isNotNull()
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid(Message.get(ATHLETE) + " " + Message.get(ID));

            int athleteIdInt = SyntaxValidator.validateInt(athleteId)
                    .isPositive()
                    .isInRange(1, 9999)
                    .throwIfInvalid(Message.get(ATHLETE) + Message.get(ID))
                    .getResult();

            SyntaxValidator.validateString(firstname)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid(Message.get(FIRSTNAME));

            SyntaxValidator.validateString(lastname)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid(Message.get(LASTNAME));

            SyntaxValidator.validateString(countryName)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid(Message.get(COUNTRY_NAME));

            SyntaxValidator.validateString(sportType)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid(Message.get(SPORTS_TYPE));

            SyntaxValidator.validateString(sportDiscipline)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid(Message.get(SPORTS_DISCIPLINE));

            OlympicSport olympicSport =  new OlympicSport(sportType, sportDiscipline);

            return new Athlete(athleteIdInt, firstname, lastname, countryName, olympicSport);
        } catch (ValidationException validationException) {
            throw new ParserException(Message.get(ATHLETE), validationException);
        }
    }
}
