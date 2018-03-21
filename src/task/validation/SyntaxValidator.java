package task.validation;

import task.validation.types.IntValidation;
import task.validation.types.StringArrayValidation;
import task.validation.types.StringValidation;

/**
 * The class that manages all the available syntax validations.
 */
public class SyntaxValidator {
    /**
     * Instantiates a new String validation.
     * @param input The String that shall be validated.
     * @return Returns a Validation object, so that validation can be chained together.
     */
    public static StringValidation validateString(String input) {
        return new StringValidation(input);
    }

    /**
     * Instantiates a new String array validation.
     * @param input The String array that shall be validated.
     * @return Returns a Validation object, so that validation can be chained together.
     */
    public static StringArrayValidation validateArray(String[] input) {
        return new StringArrayValidation(input);
    }

    /**
     * Instantiates a new Integer validation.
     * @param input The String that shall be converted to an Integer and then be validated.
     * @return Returns a Validation object, so that validation can be chained together.
     */
    public static IntValidation validateInt(String input) {
        return new IntValidation(input);
    }

    /**
     * Instantiates a new Integer validation.
     * @param input The Integer that shall be validated.
     * @return Returns a Validation object, so that validation can be chained together.
     */
    public static IntValidation validateInt(int input) {
        return new IntValidation(input);
    }
}
