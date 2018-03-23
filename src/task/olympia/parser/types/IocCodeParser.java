package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.olympia.models.IocCode;
import task.validation.SyntaxValidator;

public class IocCodeParser implements IParser {
    @Override
    public IocCode parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid("the ioc code data");

            String iocId = args[0];
            String iocCode = args[1];
            String countryName = args[2];
            String determinationYear = args[3];

            SyntaxValidator.validateString(iocId)
                    .isNotNull()
                    .isNotContaining(";")
                    .isOfLength(3)
                    .throwIfInvalid("the IOC-Id");

            int iocIdInt = SyntaxValidator.validateInt(iocId)
                    .isPositive()
                    .isInRange(1, 999)
                    .throwIfInvalid("the IOC-Id")
                    .getResult();

            SyntaxValidator.validateString(iocCode)
                    .isNotNull()
                    .isOnlyLetters()
                    .isOfLength(3)
                    .throwIfInvalid("the IOC code");

            SyntaxValidator.validateString(determinationYear)
                    .isNotNull()
                    .isOfLength(4)
                    .throwIfInvalid("the determination year");

            int determinationYearInt = SyntaxValidator.validateInt(determinationYear)
                    .isPositive()
                    .isInRange(0, 9999)
                    .throwIfInvalid("the determination year")
                    .getResult();

            return new IocCode(iocIdInt, iocCode, countryName, determinationYearInt);
        } catch (ValidationException validationException) {
            throw new ParserException(validationException.getMessage());
        }
    }
}
