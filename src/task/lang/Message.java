package task.lang;

public enum Message {
    ALREADY_LOGGED_IN ("you are already logged in."),
    THIS_$STRING$_ALREADY_EXISTS ("this %s already exists."),
    NOT_LOGGED_IN ("you are not logged in."),
    WRONG_CREDENTIALS ("the given credentials are wrong. Please try again."),
    LOGIN_FAILED ("the login failed. Please try again."),
    VALIDATION_FAILED_CHECKING_TYPES ("validation failed while trying to check the types in the passed arguments list"),
    UNDEFINED_DATATYPE ("there is an undefined data-type in the command signature"),
    SHOULD_BE_LONGER_THAN_$INT$("should be longer than %d"),
    SHOULD_BE_SHORTER_THAN_$INT$("should be shorter than %d"),
    HAS_LENGTH_$INT$_INSTEAD_OF_$INT$("has length %d instead of %d"),
    SHOULD_HAVE_LENGTH_BETWEEN_$INT$_AND_$INT$("should have length between %d and %d"),
    SHOULD_CONTAIN_$STRING$("should contain \"%s\""),
    SHOULD_NOT_CONTAIN_$STRING$("should not contain \"%s\""),
    SHOULD_NOT_BE_NULL ("should not be null"),
    SHOULD_NOT_BE_EMPTY ("should not be empty"),
    IS_NOT_AN_INTEGER ("is not an integer"),
    NUMBER ("number"),
    AND ("and");

    private String message;

    Message(String message) {
        this.message = message;
    }

    private String getMessage(Object... args) {
        return String.format(this.message, args);
    }

    public String get() {
        return this.getMessage();
    }

    public static String get(Message message, Object... args) {
        return message.getMessage(args);
    }

    public static String get(String blueprint, Object... args) {
        return String.format(blueprint, args);
    }
}
