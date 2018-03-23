package task.validation.types;

import task.constructs.math.IntRange;
import task.constructs.program.ValidationResult;
import task.exceptions.ValidationException;

/**
 * This class holds all applicable Integer validations.
 */
public class IntValidation {
    private int validateMe;
    private ValidationResult validationResult;

    /**
     * Instantiates a new Integer validation, in which all the sub-validations can be chained together.
     * @param validateMe The String that should be converted to an Integer first, and than be validated.
     */
    public IntValidation(String validateMe) {
        this.validationResult = new ValidationResult();

        try {
            this.validateMe = Integer.parseInt(validateMe);
        } catch (NumberFormatException exception) {
            addError("is not an integer");
        }
    }

    /**
     * Instantiates a new Integer validation, in which all the sub-validations can be chained together.
     * @param validateMe The Integer that shall be validated.
     */
    public IntValidation(int validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    /**
     * Checks whether the Integer is within given bounds.
     * @param lowerBound The (included) lower bound.
     * @param upperBound The (included) upper bound).
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isInRange(int lowerBound, int upperBound) {
        if (this.validateMe < lowerBound || this.validateMe > upperBound) {
            return addError(String.format("should be in between %d and %d", lowerBound, upperBound));
        }

        return this;
    }

    /**
     * Checks whether the Integer is exactly "twin".
     * @param twin The Integer that shall be checked for equality.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isExactly(int twin) {
        if (this.validateMe != twin) {
            return addError(String.format("should be exactly %d", twin));
        }

        return this;
    }

    /**
     * Checks whether the Integer is within an intRange
     * @param intRange The intRange with included lower and upper bound the Integer shall be within.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isInIntRange(IntRange intRange) {
        if (this.validateMe < intRange.getLowerBound() || this.validateMe > intRange.getUpperBound()) {
            return addError(String.format("should be in between %d and %d",
                    intRange.getLowerBound(),
                    intRange.getUpperBound())
            );
        }

        return this;
    }

    /**
     * Checks whether the Integer is greater than a given value.
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
     * @param upperBound The (excluded) upper bound.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isLessThan(int upperBound) {
        if (this.validateMe >= upperBound) {
            return addError(String.format("should be less than %d", upperBound));
        }

        return this;
    }

    /**
     * Checks whether the Integer is positive.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isPositive() {
        if (this.validateMe < 0) {
            return addError("should be positive");
        }

        return this;
    }

    /**
     * Checks whether the Integer is negative.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isNegative() {
        if (this.validateMe > 0) {
            return addError("should be negative");
        }

        return this;
    }

    /**
     * Checks whether the Integer is even.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isEven() {
        if (this.validateMe % 2 != 0) {
            return addError("should be even");
        }

        return this;
    }

    /**
     * Checks whether the Integer is odd.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isOdd() {
        if (this.validateMe % 2 == 0) {
            return addError("should be odd");
        }

        return this;
    }

    /**
     * Checks whether the Integer is a multiple of a given value.
     * @param value The value the Integer shall be a multiple of.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isMultipleOf(int value) {
        if (this.validateMe % value != 0) {
            return addError(String.format("should be a multiple of %d", value));
        }

        return this;
    }

    /**
     * Checks whether the Integer is a multiple of a given value plus an offset.
     * @param value The value the Integer shall be a multiple of.
     * @param offset The desired offset.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public IntValidation isMultipleOf(int value, int offset) {
        if (this.validateMe % value != offset) {
            return addError(String.format("should be a multiple of %d and have offset %d", value, offset));
        }

        return this;
    }

    /**
     * Throws all collected errors, if there are any.
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
    private IntValidation addError(String errorMessage) {
        validationResult.addValidationError(errorMessage);
        return this;
    }

    /**
     * Returns the validated result.
     * @return The validated Integer.
     */
    public int getResult() {
        return this.validateMe;
    }
}