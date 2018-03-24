package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.olympia.models.Athlete;
import task.olympia.models.OlympicSport;
import task.validation.SyntaxValidator;

public class AthleteParser implements IParser<Athlete> {
    @Override
    public Athlete parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(6)
                    .throwIfInvalid("the athlete data");

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
                    .throwIfInvalid("the athlete id");

            int athleteIdInt = SyntaxValidator.validateInt(athleteId)
                    .isPositive()
                    .isInRange(1, 9999)
                    .throwIfInvalid("the athlete id")
                    .getResult();

            SyntaxValidator.validateString(firstname)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid("firstname");

            SyntaxValidator.validateString(lastname)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid("lastname");

            SyntaxValidator.validateString(countryName)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid("country name");

            SyntaxValidator.validateString(sportType)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid("sport type");

            SyntaxValidator.validateString(sportDiscipline)
                    .isNotNull()
                    .isNotEmpty()
                    .throwIfInvalid("sport discipline");

            OlympicSport olympicSport =  new OlympicSport(sportType, sportDiscipline);

            return new Athlete(athleteIdInt, firstname, lastname, countryName, olympicSport);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}
