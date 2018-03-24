package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.lang.Message;
import task.olympia.models.SportsVenue;
import task.validation.SyntaxValidator;

import static task.lang.Message.*;

public class SportsVenueParser implements IParser {
    @Override
    public SportsVenue parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(6)
                    .throwIfInvalid(Message.get(SPORTS_VENUE) + " " + Message.get(DATA));

            String venueId = args[0];
            String countryName = args[1];
            String location = args[2];
            String name = args[3];
            String openingYear = args[4];
            String seatCount = args[5];

            SyntaxValidator.validateString(venueId)
                    .isNotNull()
                    .isNotEmpty()
                    .isOfLength(3)
                    .throwIfInvalid(Message.get(SPORTS_VENUE) + " " + Message.get(ID));

            int venueIdInt = SyntaxValidator.validateInt(venueId)
                    .isPositive()
                    .isInRange(1, 999)
                    .throwIfInvalid(Message.get(SPORTS_VENUE) + " " + Message.get(ID))
                    .getResult();

            SyntaxValidator.validateString(openingYear)
                    .isNotNull()
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid(Message.get(YEAR));

            int openingYearInt = SyntaxValidator.validateInt(openingYear)
                    .isPositive()
                    .isInRange(0, 9999)
                    .throwIfInvalid(Message.get(YEAR))
                    .getResult();

            int seatCountInt = SyntaxValidator.validateInt(seatCount)
                    .isPositive()
                    .throwIfInvalid(Message.get(SEAT_COUNT))
                    .getResult();

            return new SportsVenue(venueIdInt, countryName, location, name, openingYearInt, seatCountInt);
        } catch (ValidationException validationException) {
            throw new ParserException(Message.get(SPORTS_VENUE), validationException);
        }
    }
}
