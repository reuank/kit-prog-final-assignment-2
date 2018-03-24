package task.lang;

import java.util.Arrays;

public enum Message {
    ALREADY_LOGGED_IN ("you are already logged in"),
    GROUP_$STRING$_REQUIRED ("you have to be an %s"),
    NOT_LOGGED_IN ("you are not logged in"),

    COMMAND_$STRING$_COULD_NOT_BE_EXECUTED ("command \"%s\" could not be executed."),
    COMMAND_$STRING$_UNDEFINED ("the command \"%s\" is not defined."),
    REQUIRED_COMMAND_SIGNATURE_IS_$STRING$ ("The required command signature is \"%s\""),

    THIS_$STRING$_ALREADY_EXISTS ("this %s already exists"),

    NOT_OF_TYPE_$STRING$ ("is not of type %s"),

    WRONG_CREDENTIALS ("the given credentials are wrong. Please try again."),
    LOGIN_FAILED ("the tryLogin failed. Please try again."),
    VALIDATION_FAILED_CHECKING_TYPES ("validation failed while trying to check the types in the passed arguments list"),
    UNDEFINED_DATATYPE ("there is an undefined data-type in the command signature"),
    SHOULD_BE_LONGER_THAN_$INT$("should be longer than %d"),
    SHOULD_BE_SHORTER_THAN_$INT$("should be shorter than %d"),
    HAS_LENGTH_$INT$_INSTEAD_OF_$INT$("has length %d instead of %d"),
    SHOULD_HAVE_LENGTH_BETWEEN_$INT$_AND_$INT$("should have length between %d and %d"),

    SHOULD_BE_IN_BETWEEN_$INT$_AND_$INT$("should be in between %d and %d"),
    SHOULD_BE_EXACTLY_$INT$ ("should be exactly %d"),
    SHOULD_BE_GREATER_THAN_$INT$ ("should be greater than %d"),
    SHOULD_BE_POSITIVE ("should be positive"),
    SHOULD_BE_NEGATIVE ("should be negative"),
    SHOULD_BE_EVEN ("should be even"),
    SHOULD_BE_ODD ("should be odd"),
    SHOULD_BE_MULTIPLE_OF_$INT$ ("should be a multiple of %d"),
    SHOULD_BE_MULTIPLE_OF_$INT$_WITH_OFFSET_$INT$ ("should be a multiple of %d and have offset %d"),

    SHOULD_BE_EXACTLY_$STRING$ ("should be exactly %s"),

    SHOULD_HAVE_LENGTH_$INT$("should have length %d"),
    SHOULD_CONTAIN_$STRING$("should contain \"%s\""),
    SHOULD_NOT_CONTAIN_$STRING$("should not contain \"%s\""),

    SHOULD_NOT_BE_NULL ("should not be null"),
    SHOULD_NOT_BE_EMPTY ("should not be empty"),

    SHOULD_BE_EITHER ("should be either %s"),
    IS_NOT_AN_INTEGER ("is not an integer"),
    DOES_NOT_MATCH_REQUIREMENTS("does not match the requirements"),
    SHOULD_ONLY_CONTAIN_LETTERS ("should only contain letters"),
    TOO_MANY_MEDALS ("an athlete can only win a single medal per competition"),
    NOT_EXISTENT("the given %s is not present in the database"),
    NUMBER ("number"),
    ADMIN ("administrator"),
    OR ("or"),
    AND ("and"),
    BUT ("but"),
    INTEGER ("Integer"),
    BECAUSE_$STRING$ ("because %s"),
    THE_$STRING$_COULD_NOT_BE_PARSED ("the %s could not be parsed"),

    IOC_CODE ("ioc code"),
    COUNTRY_NAME ("country name"),
    OLYMPIC_SPORT ("olympic sport"),
    SPORTS_VENUE ("sports venue"),
    ATHLETE ("athlete"),
    ATHLETE_NOT_PRACTICES_SPORT ("this athlete does not practice this olympic sport"),
    USERNAME ("username"),
    COMPETITION ("competition"),


    IOC_CODE_YOUNGER_ERROR ("the corresponding ioc code was created after this year");

    private String message;

    Message(String message) {
        this.message = message;
    }

    private String getSingleFormatted(Object... args) {
        return String.format(this.message, args);
    }

    public String get() {
        return this.message;
    }

    public static String get(Message message, Object... args) {
        return String.format(message.get(), args);
    }

    public static String getChained(Message message, Message... messages) {
        StringBuilder concatenatedMessage = new StringBuilder();
        Arrays.stream(messages).forEach(msg -> concatenatedMessage.append(" " + msg.get()));

        return concatenatedMessage.toString();
    }

    public static String getFormatted(String blueprint, Object... args) {
        return String.format(blueprint, args);
    }

    public static String getOwn(String message) {
        return message;
    }

    public static String getOwn(Message message, Object... args) {
        return get(message, args);
    }
}
