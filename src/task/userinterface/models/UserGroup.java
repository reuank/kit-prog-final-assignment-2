package task.userinterface.models;

public enum UserGroup {
    ADMIN,
    WORLD;

    public static UserGroup getDefault() {
        return WORLD;
    }
}