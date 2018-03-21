package task.constructs.program;

public class Argument {
    private String argName;
    private Datatype argType;

    public Argument(String argName, Datatype argType) {
        this.argName = argName;
        this.argType = argType;
    }

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }

    public Datatype getArgType() {
        return argType;
    }

    public void setArgType(Datatype argType) {
        this.argType = argType;
    }
}