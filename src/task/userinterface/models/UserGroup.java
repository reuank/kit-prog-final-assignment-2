package task.userinterface.models;

public enum UserGroup {
    /**
     * A admin
     */
    ADMIN,

    /**
     * A guest
     */
    GUEST;

    /**
     * @return - returns the default user group with no rights.
     */
    public static UserGroup getDefault() {
        return GUEST;
    }
}