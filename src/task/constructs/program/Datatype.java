package task.constructs.program;

public enum Datatype {
    INT ("int"),
    STRING ("String");

    private String name;

    Datatype(String name) {
        this.name = name;
    }
}