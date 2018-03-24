package task.userinterface.auth;

import task.exceptions.AuthException;
import task.lang.Message;

import java.util.Arrays;
import java.util.stream.Collectors;

import static task.lang.Message.*;

/**
 * Permissions are used to control access to restricted commands
 */
public enum Permission {
    /**
     * When a user have to be logged in.
     */
    MUST_BE_LOGGED_IN (Message.get(Message.NOT_LOGGED_IN)),

    /**
     * When a user has to be an admin.
     */
    MUST_BE_ADMIN (Message.get(Message.GROUP_$STRING$_REQUIRED, Message.get(ADMIN))),

    /**
     * When no user shall be logged in already.
     */
    MUST_BE_LOGGED_OUT (Message.get(Message.ALREADY_LOGGED_IN));

    private String message;

    /**
     * Instantiates a nes permission.
     * @param message the error message that is printed if the right is not assigned.
     */
    Permission(String message) {
        this.message = message;
    }

    /**
     * @return - returns the error message that is assigned to the permission.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Checks whether all permissions are assigned to the session.
     *
     * @param requiredPermissions The required permissions.
     * @param session The session.
     * @throws AuthException thrown if not all permissions are assigned.
     */
    public static void requirePermissions(Permission[] requiredPermissions, Session session) throws AuthException {
        if (requiredPermissions == null) return;

        if (Arrays.stream(requiredPermissions).allMatch(perm -> perm.assignedTo(session))) return;

        String exception = Arrays.stream(requiredPermissions)
                .filter(requiredPermission -> !requiredPermission.assignedTo(session))
                .map(Permission::getMessage)
                .collect(Collectors.joining(
                        Message.getOwnFormatted(" " + AND.get() + " "), "", "."
                ));

        throw new AuthException(exception);
    }

    private boolean assignedTo(Session session) {
        if (!session.exists() && this != MUST_BE_LOGGED_OUT) {
            return false;
        }

        switch (this) {
            case MUST_BE_LOGGED_IN:
                return session.exists();
            case MUST_BE_LOGGED_OUT:
                return !session.exists();
            case MUST_BE_ADMIN:
                return session.getUser().getUserGroup() == UserGroup.ADMIN;
            default:
                return false;
        }
    }
}
