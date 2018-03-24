package task.userinterface.auth;

/**
 * Used to assign a user group and by this permissions to users.
 */
public enum UserGroup {
    /** A admin */
    ADMIN,

    /** A guest */
    GUEST;

    /**
     * @return - returns the default user group with no rights.
     */
    public static UserGroup getDefault() {
        return GUEST;
    }
}