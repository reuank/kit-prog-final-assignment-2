package task.lang;

/**
 * All error messages and sentence fragments are stored here.
 * Makes translation of the whole program easy.
 */
public enum Message {

    /* ------------ COMMAND EXECUTION ------------ */
    /** A message */
    COMMAND_$STRING$_UNDEFINED ("the command \"%s\" is not defined."),

    /** A message */
    REQUIRED_COMMAND_SIGNATURE_IS_$STRING$ ("The required command signature is \"%s\""),

    /** A message */
    COMMAND_$STRING$_COULD_NOT_BE_EXECUTED ("command \"%s\" could not be executed."),


    /* ------------ DATABASE ------------ */
    /** A message */
    THIS_$STRING$_ALREADY_EXISTS ("this %s already exists."),

    /** A message */
    DOES_NOT_MATCH_REQUIREMENTS("does not match the requirements."),

    /** A message */
    THIS_$STING$_IS_NOT_EXISTENT("the given %s is not present in the database."),

    /** A message */
    TOO_MANY_MEDALS ("an athlete can only win a single medal per competition."),

    /** A message */
    ATHLETE_NOT_PRACTICES_SPORT ("this athlete does not practice this olympic sport."),

    /** A message */
    ATHLETE_EXISTS_BUT_MISMATCH ("this athlete exists but holds other data."),

    /** A message */
    ATHLETE_SPORTS_COMBO ("athlete-olympic sport combination"),

    /** A message */
    IOC_CODE_YOUNGER_ERROR ("the corresponding ioc code was created after this year."),


    /* ------------ AUTHENTICATION ------------ */
    /** A message */
    ALREADY_LOGGED_IN ("you are already logged in"),

    /** A message */
    GROUP_$STRING$_REQUIRED ("you have to be an %s"),

    /** A message */
    NOT_LOGGED_IN ("you are not logged in"),

    /** A message */
    WRONG_CREDENTIALS ("the given credentials are wrong. Please try again."),


    /* ------------ GENERAL VALIDATION / PARSING ------------ */
    /** A message */
    NUMBER ("number"),

    /** A message */
    DATA ("data"),

    /** A message */
    THE_$STRING$_COULD_NOT_BE_PARSED ("the %s could not be parsed"),

    /** A message */
    SHOULD_NOT_BE_NULL ("should not be null"),

    /** A message */
    VALIDATION_FAILED_CHECKING_TYPES ("validation failed while trying to check the types in the passed arguments list"),

    /** A message */
    UNDEFINED_DATATYPE ("there is an undefined data-type in the command signature"),

    /** A message */
    NOT_OF_TYPE_$STRING$ ("is not of type %s"),


    /* ------------ INTEGER VALIDATION ------------ */
    /** A message */
    INTEGER ("Integer"),

    /** A message */
    SHOULD_BE_POSITIVE ("should be positive"),

    /** A message */
    SHOULD_BE_NEGATIVE ("should be negative"),

    /** A message */
    SHOULD_BE_IN_BETWEEN_$INT$_AND_$INT$("should be in between %d and %d"),

    /** A message */
    SHOULD_BE_EXACTLY_$INT$ ("should be exactly %d"),

    /** A message */
    SHOULD_BE_GREATER_THAN_$INT$ ("should be greater than %d"),

    /** A message */
    SHOULD_BE_LESS_THAN_$INT$ ("should be less than %d"),

    /** A message */
    SHOULD_BE_EVEN ("should be even"),

    /** A message */
    SHOULD_BE_ODD ("should be odd"),

    /** A message */
    SHOULD_BE_MULTIPLE_OF_$INT$ ("should be a multiple of %d"),

    /** A message */
    SHOULD_BE_MULTIPLE_OF_$INT$_WITH_OFFSET_$INT$ ("should be a multiple of %d and have offset %d"),


    /* ------------ STRING / STRING ARRAY VALIDATION ------------ */
    /** A message */
    SHOULD_BE_EXACTLY_$STRING$ ("should be exactly %s"),

    /** A message */
    SHOULD_HAVE_LENGTH_$INT$("should have length %d"),

    /** A message */
    SHOULD_CONTAIN_$STRING$("should contain \"%s\""),

    /** A message */
    SHOULD_NOT_CONTAIN_$STRING$("should not contain \"%s\""),

    /** A message */
    SHOULD_NOT_BE_EMPTY ("should not be empty"),

    /** A message */
    SHOULD_BE_LONGER_THAN_$INT$("should be longer than %d"),

    /** A message */
    SHOULD_BE_SHORTER_THAN_$INT$("should be shorter than %d"),

    /** A message */
    HAS_LENGTH_$INT$_INSTEAD_OF_$INT$("has length %d instead of %d"),

    /** A message */
    SHOULD_HAVE_LENGTH_BETWEEN_$INT$_AND_$INT$("should have length between %d and %d"),

    /** A message */
    SHOULD_BE_EITHER ("should be either %s"),

    /** A message */
    SHOULD_ONLY_CONTAIN_LETTERS ("should only contain letters"),


    /* ------------ MODELS AND ATTRIBUTES ------------ */
    /** A message */
    COMMAND ("command"),

    /** A message */
    ADMIN ("administrator"),

    /** A message */
    USER ("user"),

    /** A message */
    USERNAME ("username"),

    /** A message */
    PASSWORD ("password"),

    /** A message */
    YEAR ("year"),

    /** A message */
    IOC_CODE ("ioc code"),

    /** A message */
    COUNTRY_NAME ("country name"),

    /** A message */
    OLYMPIC_SPORT ("olympic sport"),

    /** A message */
    SPORTS_VENUE ("sports venue"),

    /** A message */
    ATHLETE ("athlete"),

    /** A message */
    COMPETITION ("competition"),

    /** A message */
    FIRSTNAME ("firstname"),

    /** A message */
    LASTNAME ("lastname"),

    /** A message */
    ID ("id"),

    /** A message */
    SPORTS_TYPE ("sports type"),

    /** A message */
    SPORTS_DISCIPLINE ("sport discipline"),

    /** A message */
    GOLD ("gold"),

    /** A message */
    SILVER ("silver"),

    /** A message */
    BRONZE ("bonze"),

    /** A message */
    MEDAL_COUNT ("medal count"),

    /** A message */
    SEAT_COUNT ("seat count"),


    /* ------------ WORDS AND FILLERS ------------ */
    /** A message */
    THE ("the"),

    /** A message */
    OR ("or"),

    /** A message */
    AND ("and"),

    /** A message */
    BUT ("but"),

    /** A message */
    BECAUSE_$STRING$ ("because %s");


    private String message;

    /**
     * Instantiates a new message enum
     * @param message the message
     */
    Message(String message) {
        this.message = message;
    }

    /**
     * @return returns the assigned message
     */
    public String get() {
        return this.message;
    }

    /**
     * Performs a string.format operation with a message.
     *
     * @param message the message
     * @param args the parameters that shall be
     * @return returns the formatted message
     */
    public static String get(Message message, Object... args) {
        return String.format(message.get(), args);
    }

    /**
     * Returns a custom formatted message. This can be used to find all messages that still need to be translated.
     *
     * @param blueprint the message blueprint
     * @param args the args
     * @return returns the custom formatted message
     */
    public static String getOwnFormatted(String blueprint, Object... args) {
        return String.format(blueprint, args);
    }

    /**
     * Returns a custom message. This can be used to find all messages that still need to be translated.
     *
     * @param message the message
     * @return returns the message
     */
    public static String getOwn(String message) {
        return message;
    }
}
