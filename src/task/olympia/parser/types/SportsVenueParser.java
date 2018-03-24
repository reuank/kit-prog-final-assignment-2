package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.olympia.models.SportsVenue;
import task.validation.SyntaxValidator;

public class SportsVenueParser implements IParser {
    @Override
    public SportsVenue parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(6)
                    .throwIfInvalid("the sports venue data");

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
                    .throwIfInvalid("the sports venue Id");

            int venueIdInt = SyntaxValidator.validateInt(venueId)
                    .isPositive()
                    .isInRange(1, 999)
                    .throwIfInvalid("the sports venue id")
                    .getResult();

            SyntaxValidator.validateString(openingYear)
                    .isNotNull()
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid("the opening year");

            int openingYearInt = SyntaxValidator.validateInt(openingYear)
                    .isPositive()
                    .isInRange(0, 9999)
                    .throwIfInvalid("the opening year")
                    .getResult();

            int seatCountInt = SyntaxValidator.validateInt(seatCount)
                    .isPositive()
                    .throwIfInvalid("the seat count")
                    .getResult();

            return new SportsVenue(venueIdInt, countryName, location, name, openingYearInt, seatCountInt);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}
