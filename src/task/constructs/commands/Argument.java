package task.constructs.commands;

/**
 * The construct to hold command argument data
 */
public class Argument {
    private String argName;
    private Datatype argType;

    /**
     * Instantiates a new Argument
     *
     * @param argName the name of the argument
     * @param argType the data-type of the argument
     */
    public Argument(String argName, Datatype argType) {
        this.argName = argName;
        this.argType = argType;
    }

    /**
     * @return - returns the argName
     **/
    public String getArgName() {
        return argName;
    }

    /**
     * Sets the argName
     *
     * @param argName - the new argName
     **/
    public void setArgName(String argName) {
        this.argName = argName;
    }

    /**
     * @return - returns the argType
     **/
    public Datatype getArgType() {
        return argType;
    }

    /**
     * Sets the argType
     *
     * @param argType - the new argType
     **/
    public void setArgType(Datatype argType) {
        this.argType = argType;
    }
}