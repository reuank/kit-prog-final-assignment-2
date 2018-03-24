package task.validation;

import task.lang.Message;

import static task.lang.Message.OR;

/**
 * In a ValidationResult, all the messages of a validation getOwnFormatted stored, so that a complete validation
 * can happen before the error message gets printed.
 */
public class ValidationResult {
    private boolean failed;
    private StringBuilder message;

    /**
     * Returns a brand new Validation Result Object.
     */
    public ValidationResult() {
        this.failed = false;
        this.message = new StringBuilder();
    }

    /**
     * Appends an error message to the Validation Result message.
     * As soon as a message gets added, the validation has failed.
     * @param message The message that shall be appended.
     */
    public void addValidationError(String message) {
        this.failed = true;
        this.message.append(this.message.toString().equals("") ? message : Message.get(OR) + message);
    }

    /**
     * Whether or not the Validation has failed.
     * @return Returns true if the validation has failed.
     */
    public boolean failed() {
        return this.failed;
    }

    /**
     * Gets the message that has been generated during the validation process.
     * @return Returns the message as a string.
     */
    public String getMessage() {
        return message == null ? null : message.toString();
    }
}