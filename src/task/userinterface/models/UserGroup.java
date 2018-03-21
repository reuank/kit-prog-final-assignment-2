package task.userinterface.models;

public enum UserGroup {
    ADMIN,
    GUEST;

    public static UserGroup getDefault() {
        return GUEST;
    }
}