package task.constructs.program;

/**
 * The enum that is used to model different datatypes, so that requirements can be defined precisely
 */
public enum Datatype {
    /**
     * The enum representation of an Integer
     */
    INT ("Integer"),

    /**
     * The enum representation of a String
     */
    STRING ("String");

    private String name;

    /**
     * Instantiates a new Datatype
     * @param name The name of the datatype
     */
    Datatype(String name) {
        this.name = name;
    }

    /**
     * @return - returns the name
     **/
    public String getName() {
        return name;
    }
}