package task.validation.types;

import task.constructs.program.Datatype;
import task.constructs.program.ValidationResult;
import task.lang.Message;
import task.exceptions.ValidationException;
import task.validation.SyntaxValidator;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static task.lang.Message.*;

/**
 * This class holds all applicable String-array validations.
 */
public class StringArrayValidation {
    private String[] validateMe;
    private ValidationResult validationResult;

    /**
     * Instanciates a new String-array validation, in which all the sub-validations can be chained together.
     * @param validateMe The String-array that shall be validated.
     */
    public StringArrayValidation(String[] validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    /**
     * Checks whether the String-array is not null.
     * @return Returns the validation object if there is something in the array, else it adds an error message as well.
     */
    public StringArrayValidation isNotNull() {
        if (this.validateMe == null) {
            return addError(Message.get(SHOULD_NOT_BE_NULL));
        }

        return this;
    }

    /**
     * Checks whether there are actual values inside the String-array. Adds an error if necessary.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isNotEmpty() {
        for (int i = 0; i < this.validateMe.length; i++) {
            if (!this.validateMe[0].equals("")) { // Found something!
                return this;
            }
        }

        return addError(Message.get(SHOULD_NOT_BE_EMPTY));
    }

    /**
     * Checks whether the String-array has a size bigger or equal to a given value. Adds an error if necessary.
     * @param length The length the String-array shall have at least.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isLongerOrEqualThan(int length) {
        if (this.validateMe == null) {
            return addError(Message.get(SHOULD_NOT_BE_NULL));
        }

        if (this.validateMe.length <= length) {
            return addError(Message.get(SHOULD_BE_LONGER_THAN_$INT$, length));
        }

        return this;
    }

    /**
     * Checks whether the String-array has a size smaller or equal to a given value. Adds an error if necessary.
     * @param length The length the String-array shall have at most.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isShorterOrEqualThan(int length) {
        if (this.validateMe.length >= length) {
            return addError(Message.get(SHOULD_BE_SHORTER_THAN_$INT$, length));
        }

        return this;
    }

    /**
     * Checks whether the String-array has a database length. Adds an error if necessary.
     * @param length The length the array shall have.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isOfLength(int length) {
        if (this.validateMe == null) {
            if (length == 0) {
                return this;
            }

            return addError(String.format(Message.get(HAS_LENGTH_$INT$_INSTEAD_OF_$INT$), 0, length));
        } else if (this.validateMe.length != length ) {
            return addError(Message.get(HAS_LENGTH_$INT$_INSTEAD_OF_$INT$, this.validateMe.length, length));
        }

        return this;
    }

    /**
     * Checks whether the String-arrays length is in the given bounds. Adds an error if necessary.
     * @param lowerBound The lower (included) bound of the size.
     * @param upperBound The upper (included) bound of the size.
     * @return  Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isOfLengthBetween(int lowerBound, int upperBound) {
        if (this.validateMe == null && lowerBound == 0 && upperBound == 0 ) {
            return this;
        }

        if (this.validateMe.length < lowerBound || this.validateMe.length > upperBound) {
            return addError(Message.get(SHOULD_HAVE_LENGTH_BETWEEN_$INT$_AND_$INT$, lowerBound, upperBound));
        }

        return this;
    }

    /**
     * Checks whether the String-array contains a database String value. Adds an error if necessary.
     * @param needle The String that shall be searched for.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isContaining(String needle) {
        if (this.validateMe == null) {
            return addError(Message.get(SHOULD_NOT_BE_NULL, needle));
        }

        for (String value : this.validateMe) {
            if (value.equals(needle)) { // Desired String found!
                return this;
            }
        }

        return addError(Message.get(SHOULD_CONTAIN_$STRING$, needle));
    }

    /**
     * This method can be used to ensure that the values in a string array can be converted to a database data-type.
     * Currently the following data-type-checks are supported: int.
     * In the future, there can be added float-checks, regex checks or anything alike.
     * @param argTypes This is the list of datatypes that validateMe should be checked against
     * @return Returns the current Validation object, so that other validation can be applied.
     * @throws ValidationException If the check fails or the input parameters are false, an exception will be thrown.
     */
    public StringArrayValidation checkTypes(Datatype[] argTypes) throws ValidationException {
        // Ensure that there is something to check
        if (this.validateMe == null) {
            // No need to check anything
            if (argTypes == null) {
                return this;
            }

            return addError(Message.get(VALIDATION_FAILED_CHECKING_TYPES));
        }

        // Number of types to check not equals number of given types
        if (this.validateMe.length != argTypes.length) {
            return addError(Message.get(VALIDATION_FAILED_CHECKING_TYPES));
        }

        ArrayList<Integer> failedIntParams = new ArrayList<>();

        for (int i = 0; i < this.validateMe.length; i++) {
            switch (argTypes[i]) {
                case INT:
                    if (SyntaxValidator.validateInt(this.validateMe[i]).hasFailed()) {
                        failedIntParams.add(i + 1);
                    }
                    break;
                case STRING:
                    //TODO Add String validations here
                    break;
                default:
                    throw new ValidationException(Message.get(UNDEFINED_DATATYPE));
            }
        }

        if (!failedIntParams.isEmpty()) {
            String errorPhrase = failedIntParams.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(
                            Message.get(" " + AND.get() + " "),
                            Message.get(NUMBER),
                            Message.get(IS_NOT_AN_INTEGER)
                    ));

            addError(errorPhrase);
        }

        return this;
    }

    /**
     * Throws all collected errors, if there are any.
     * @param paramName The name of the validation object that shall occur in the error message.
     * @return Returns the current Validation object, so that other validation can be applied.
     * @throws ValidationException Thrown if any errors occurred so far.
     */
    public StringArrayValidation throwIfInvalid(String paramName) throws ValidationException {
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
    private StringArrayValidation addError(String errorMessage) {
        if (errorMessage == null || errorMessage.equals("")) { // No errors to be added. Empty call.
            return this;
        }

        this.validationResult.addValidationError(errorMessage);
        return this;
    }

    /**
     * Returns the validated result.
     * @return The validated String-array.
     */
    public String[] getResult() {
        return this.validateMe;
    }
}
