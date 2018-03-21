package task.validation.types;

import task.constructs.program.ValidationResult;
import task.exceptions.ValidationException;
import task.lang.Message;

import java.util.Arrays;
import java.util.stream.Collectors;

import static task.lang.Message.*;

/**
 * This class holds all applicable String validations.
 */
public class StringValidation {
    private String validateMe;
    private ValidationResult validationResult;

    /**
     * Instantiates a new String validation, in which all the sub-validations can be chained together.
     * @param validateMe The String that shall be validated.
     */
    public StringValidation(String validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    /**
     * Checks whether the String is exactly "twin".
     * @param twin The String that shall be checked for equality.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringValidation isExactly(String twin) {
        if ( !this.validateMe.equals(twin)) {
            return addError(String.format("should be exactly %s", twin));
        }

        return this;
    }

    /**
     * Checks whether the String is not null.
     * @return Returns the validation object if there is something in the array, else it adds an error message as well.
     */
    public StringValidation isNotNull() {
        if (validateMe == null) {
            return addError("should not be null");
        }

        return this;
    }

    /**
     * Checks whether the String-arrays length is in the given bounds. Adds an error if necessary.
     * @param lowerBound The lower (included) bound of the size.
     * @param upperBound The upper (included) bound of the size.
     * @return  Returns the current Validation object, so that other validation can be applied.
     */
    public StringValidation isOfLengthBetween(int lowerBound, int upperBound) {
        if (this.validateMe == null && lowerBound == 0 && upperBound == 0 ) {
            return this;
        }

        if (this.validateMe.length() < lowerBound || this.validateMe.length() > upperBound) {
            return addError(Message.get(SHOULD_HAVE_LENGTH_BETWEEN_$INT$_AND_$INT$, lowerBound, upperBound));
        }

        return this;
    }


    public StringValidation isOfLength(int length) {
        if (this.validateMe == null && length == 0) {
            return this;
        }

        if (this.validateMe.length() != length) {
            return addError(Message.get(SHOULD_HAVE_LENGTH_$INT$, length));
        }

        return this;
    }


    public StringValidation matches(String regex) {
        if (this.validateMe == null && regex == null) {
            return this;
        }

        if (!this.validateMe.matches(regex)) {
            return addError(Message.get(INPUT_NOT_MATCHES_REQUIREMENTS));
        }

        return this;
    }

    public StringValidation isOnlyLetters() {
        if (!this.validateMe.matches("[a-zA-Z]+")) {
            return addError(Message.get(SHOULD_ONLY_CONTAIN_LETTERS));
        }

        return this;
    }

    public String getValidateMe() {
        return validateMe;
    }

    /**
     * Checks whether the String contains a String value. Adds an error if necessary.
     * @param needle The String that shall be searched for.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringValidation isContaining(String needle) {
        if (this.validateMe == null) {
            return addError(Message.get(SHOULD_NOT_BE_NULL, needle));
        }

        if (this.validateMe.contains(needle)) {
            return this;
        }

        return addError(Message.get(SHOULD_CONTAIN_$STRING$, needle));
    }

    /**
     * Checks whether the String contains a String value. Adds an error if necessary.
     * @param needle The String that shall be searched for.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringValidation isNotContaining(String needle) {
        if (this.validateMe == null) {
            return addError(Message.get(SHOULD_NOT_BE_NULL, needle));
        }

        if (this.validateMe.contains(needle)) {
            return addError(Message.get(SHOULD_NOT_CONTAIN_$STRING$, needle));
        }

        return this;
    }

    /**
     * Checks whether the String is in a Set of Strings, represented by a String array.
     * @param set The String array the String shall be in.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringValidation isInSet(String[] set) {
        for (int i = 0; i < set.length; i++) {
            if (set[i].equals(validateMe)) {
                return this;
            }
        }

        String setRepresentation = Arrays.stream(set).collect(Collectors.joining(" " + Message.get(OR) + " "));

        return addError(Message.get(SHOULD_BE_EITHER, setRepresentation));
    }

    /**
     * Throws all collected errors, if there are any.
     * @param paramName The name of the validation object that shall occur in the error message.
     * @return Returns the current Validation object, so that other validation can be applied.
     * @throws ValidationException Thrown if any errors occurred so far.
     */
    public StringValidation throwIfInvalid(String paramName) throws ValidationException {
        if (this.hasFailed()) {
            throw new ValidationException(paramName, this.getErrors());
        }

        return this;
    }

    /**
     * Checks if the validation has failed so far.
     * @return Returns true if an error occurred so far.
     */
    public boolean hasFailed() {
        return this.validationResult.failed();
    }

    /**
     * Gets the error messages that have been chained together so far.
     * @return The error messages.
     */
    public String getErrors() {
        return this.validationResult.getMessage();
    }

    /**
     * Adds an errorMessage message to the error-message String of the validation object.
     * @param errorMessage The message that shall be added.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    private StringValidation addError(String errorMessage) {
        if (errorMessage == null || errorMessage.equals("")) { // No errors to be added. Empty call.
            return this;
        }

        this.validationResult.addValidationError(errorMessage);
        return this;
    }

    /**
     * Returns the validated result.
     * @return The validated String.
     */
    public String getResult() {
        return this.validateMe;
    }
}