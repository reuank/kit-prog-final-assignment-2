package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.lang.Message;
import task.olympia.models.*;
import task.validation.SyntaxValidator;

import static task.lang.Message.*;

public class CompetitionParser implements IParser<Competition> {
    @Override
    public Competition parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(8)
                    .throwIfInvalid(Message.get(COMPETITION) + " " + Message.get(DATA));

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
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid(Message.get(ATHLETE) + " " + Message.get(ID));

            int athleteIdInt = SyntaxValidator.validateInt(athleteId)
                    .isPositive()
                    .isInRange(1, 9999)
                    .throwIfInvalid(Message.get(ATHLETE) + " " + Message.get(ID))
                    .getResult();

            int yearInt = SyntaxValidator.validateInt(year)
                    .isInRange(1926, 2018)
                    .isMultipleOf(4, 2)
                    .throwIfInvalid(Message.get(YEAR))
                    .getResult();

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

            int goldMedalCountInt = SyntaxValidator.validateInt(goldMedalCount)
                    .isInRange(0, 1)
                    .throwIfInvalid(Message.get(GOLD) + " " + Message.get(MEDAL_COUNT))
                    .getResult();

            int silverMedalCountInt = SyntaxValidator.validateInt(silverMedalCount)
                    .isInRange(0, 1)
                    .throwIfInvalid(Message.get(SILVER) + " " + Message.get(MEDAL_COUNT))
                    .getResult();

            int bronzeMedalCountInt = SyntaxValidator.validateInt(bronzeMedalCount)
                    .isInRange(0, 1)
                    .throwIfInvalid(Message.get(BRONZE) + " " + Message.get(MEDAL_COUNT))
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
            throw new ParserException(Message.get(COMPETITION), validationException);
        }
    }
}
