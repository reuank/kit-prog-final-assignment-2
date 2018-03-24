package task.olympia.parser.types;

import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.IParser;
import task.lang.Message;
import task.olympia.models.IocCode;
import task.validation.SyntaxValidator;

import static task.lang.Message.*;

public class IocCodeParser implements IParser {
    @Override
    public IocCode parse(String... args) throws ParserException {
        try {
            SyntaxValidator.validateArray(args)
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid(Message.get(IOC_CODE) + " " + Message.get(DATA));

            String iocId = args[0];
            String iocCode = args[1];
            String countryName = args[2];
            String determinationYear = args[3];

            SyntaxValidator.validateString(iocId)
                    .isNotNull()
                    .isNotEmpty()
                    .isOfLength(3)
                    .throwIfInvalid(Message.get(IOC_CODE) + " " + Message.get(ID));

            int iocIdInt = SyntaxValidator.validateInt(iocId)
                    .isPositive()
                    .isInRange(1, 999)
                    .throwIfInvalid(Message.get(IOC_CODE) + " " + Message.get(ID))
                    .getResult();

            SyntaxValidator.validateString(iocCode)
                    .isNotNull()
                    .isNotEmpty()
                    .isOfLength(3)
                    .throwIfInvalid(Message.get(IOC_CODE));

            SyntaxValidator.validateString(determinationYear)
                    .isNotNull()
                    .isNotEmpty()
                    .isOfLength(4)
                    .throwIfInvalid(Message.get(YEAR));

            int determinationYearInt = SyntaxValidator.validateInt(determinationYear)
                    .isPositive()
                    .isInRange(0, 9999)
                    .throwIfInvalid(Message.get(YEAR))
                    .getResult();

            return new IocCode(iocIdInt, iocCode, countryName, determinationYearInt);
        } catch (ValidationException validationException) {
            throw new ParserException(Message.get(IOC_CODE), validationException);
        }
    }
}
