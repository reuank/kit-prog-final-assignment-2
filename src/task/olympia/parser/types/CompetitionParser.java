package task.olympia.parser.types;

import org.omg.CORBA.portable.ValueOutputStream;
import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.lang.Message;
import task.olympia.models.*;
import task.validation.SyntaxValidator;

import static task.lang.Message.TOO_MANY_MEDALS;

public class CompetitionParser implements IParser<Competition> {
    @Override
    public Competition parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(8)
                    .throwIfInvalid("the competition data");

            String athleteId = args[0];
            String year = args[1];
            String countryName = args[2];
            String sportType = args[3];
            String sportDiscipline = args[4];
            String goldMedalCount = args[5];
            String silverMedalCount = args[6];
            String bronzeMedalCount = args[7];

            SyntaxValidator.validateString(athleteId)
                    .isNotNull()
                    .isOfLength(4)
                    .throwIfInvalid("the athlete id");

            int athleteIdInt = SyntaxValidator.validateInt(athleteId)
                    .isPositive()
                    .isInRange(1, 9999)
                    .throwIfInvalid("the athlete id")
                    .getResult();

            int yearInt = SyntaxValidator.validateInt(year)
                    .isInRange(1926, 2018)
                    .isMultipleOf(4, 2)
                    .throwIfInvalid("year")
                    .getResult();

            SyntaxValidator.validateString(countryName)
                    .isNotNull()
                    .throwIfInvalid("country name");

            SyntaxValidator.validateString(sportType)
                    .isNotNull()
                    .throwIfInvalid("sport type");

            SyntaxValidator.validateString(sportDiscipline)
                    .isNotNull()
                    .throwIfInvalid("sport discipline");

            int goldMedalCountInt = SyntaxValidator.validateInt(goldMedalCount)
                    .isInRange(0, 1)
                    .throwIfInvalid("gold medal count")
                    .getResult();

            int silverMedalCountInt = SyntaxValidator.validateInt(silverMedalCount)
                    .isInRange(0, 1)
                    .throwIfInvalid("silver medal count")
                    .getResult();

            int bronzeMedalCountInt = SyntaxValidator.validateInt(bronzeMedalCount)
                    .isInRange(0, 1)
                    .throwIfInvalid("bronze medal count")
                    .getResult();

            if (goldMedalCountInt + silverMedalCountInt + bronzeMedalCountInt > 1) {
                throw new ParserException(Message.get(TOO_MANY_MEDALS));
            }

            Athlete athlete = new Athlete(athleteIdInt);
            IocCode iocCode = new IocCode(countryName);
            OlympicSport olympicSport =  new OlympicSport(sportType, sportDiscipline);
            Medal medal = Medal.getMedal(goldMedalCountInt, silverMedalCountInt, bronzeMedalCountInt);

            return new Competition(athlete, yearInt, iocCode, olympicSport, medal);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}
