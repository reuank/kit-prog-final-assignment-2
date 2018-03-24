package task.validation.types;

import task.lang.Message;
import task.validation.ValidationResult;
import task.exceptions.ValidationException;

import static task.lang.Message.*;

/**
 * This class holds all applicable Integer validations.
 */
public class IntValidation {
    private int validateMe;
    private ValidationResult validationResult;

    /**
     * Instantiates a new Integer validation, in which all the sub-validations can be chained together.
     *
     * @param validateMe The String that should be converted to an Integer first, and than be validated.
     */
    public IntValidation(String validateMe) {
        this.validationResult = new ValidationResult();

        try {
            this.validateMe = Integer.parseInt(validateMe);
        } catch (NumberFormatException exception) {
            addError(Message.get(NOT_OF_TYPE_$STRING$, INTEGER));
        }
    }

    /**
     * Instantiates a new Integer validation, in which all the sub-validations can be chained together.
     *
     * @param validateMe The Integer that shall be validated.
     */
    public IntValidation(int validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    /**
     * Checks whether the Integer is within given bounds.
     *
     * @param lowerBound The (included) lower bound.
     * @param upperBound The (included) upper bound).
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isInRange(int lowerBound, int upperBound) {
        if (this.validateMe < lowerBound || this.validateMe > upperBound) {
            return addError(Message.get(SHOULD_BE_IN_BETWEEN_$INT$_AND_$INT$, lowerBound, upperBound));
        }

        return this;
    }

    /**
     * Checks whether the Integer is exactly "twin".
     *
     * @param twin The Integer that shall be checked for equality.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isExactly(int twin) {
        if (this.validateMe != twin) {
            return addError(Message.get(SHOULD_BE_EXACTLY_$INT$, twin));
        }

        return this;
    }

    /**
     * Checks whether the Integer is greater than a given value.
     *
     * @param lowerBound The (excluded) lower bound.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isGreaterThan(int lowerBound) {
        if (this.validateMe <= lowerBound) {
            return addError(String.format("should be greater than %d", lowerBound));
        }

        return this;
    }

    /**
     * Checks whether the Integer is less then a given value.
     *
     * @param upperBound The (excluded) upper bound.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isLessThan(int upperBound) {
        if (this.validateMe >= upperBound) {
            return addError(Message.get(SHOULD_BE_GREATER_THAN_$INT$, upperBound));
        }

        return this;
    }

    /**
     * Checks whether the Integer is positive.
     *
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isPositive() {
        if (this.validateMe < 0) {
            return addError(Message.getChained(SHOULD_BE_POSITIVE));
        }

        return this;
    }

    /**
     * Checks whether the Integer is negative.
     *
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isNegative() {
        if (this.validateMe > 0) {
            return addError(Message.getChained(SHOULD_BE_NEGATIVE));
        }

        return this;
    }

    /**
     * Checks whether the Integer is even.
     *
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isEven() {
        if (this.validateMe % 2 != 0) {
            return addError(Message.getChained(SHOULD_BE_EVEN));
        }

        return this;
    }

    /**
     * Checks whether the Integer is odd.
     *
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isOdd() {
        if (this.validateMe % 2 == 0) {
            return addError(Message.getChained(SHOULD_BE_ODD));
        }

        return this;
    }

    /**
     * Checks whether the Integer is a multiple of a given value.
     *
     * @param value The value the Integer shall be a multiple of.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isMultipleOf(int value) {
        if (this.validateMe % value != 0) {
            return addError(Message.get(SHOULD_BE_MULTIPLE_OF_$INT$, value));
        }

        return this;
    }

    /**
     * Checks whether the Integer is a multiple of a given value plus an offset.
     *
     * @param value The value the Integer shall be a multiple of.
     * @param offset The desired offset.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isMultipleOf(int value, int offset) {
        if (this.validateMe % value != offset) {
            return addError(Message.get(SHOULD_BE_MULTIPLE_OF_$INT$_WITH_OFFSET_$INT$, value, offset));
        }

        return this;
    }

    /**
     * Throws all collected errors, if there are any.
     *
     * @param paramName The name of the validation object that shall occur in the error message.
     * @return Returns the current Validation object, so that other validation can be applied.
     * @throws ValidationException Thrown if any errors occurred so far.
     */
    public IntValidation throwIfInvalid(String paramName) throws ValidationException {
        if (this.hasFailed()) {
            throw new ValidationException(paramName, this.getErrors());
        }

        return this;
    }

    /**
     * Checks if the validation has failed so far.
     *
     * @return Returns true if an error occurred so far.
     */
    public boolean hasFailed() {
        return this.validationResult.failed();
    }

    /**
     * Gets the error messages that have been chained together so far.
     *
     * @return The error messages.
     */
    public String getErrors() {
        return this.validationResult.getMessage();
    }

    /**
     * Adds an errorMessage message to the error-message String of the validation object.
     *
     * @param errorMessage The message that shall be added.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    private IntValidation addError(String errorMessage) {
        validationResult.addValidationError(errorMessage);
        return this;
    }

    /**
     * Returns the validated result.
     *
     * @return The validated Integer.
     */
    public int getResult() {
        return this.validateMe;
    }
}