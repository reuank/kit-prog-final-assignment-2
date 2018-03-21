package task.userinterface.auth;

import task.exceptions.AuthException;
import task.lang.Message;
import task.userinterface.models.UserGroup;

import java.util.Arrays;
import java.util.stream.Collectors;

import static task.lang.Message.*;

public enum Permission {
    MUST_BE_LOGGED_IN (Message.get(Message.NOT_LOGGED_IN)),
    MUST_BE_ADMIN (Message.get(Message.GROUP_$STRING$_REQUIRED, Message.get(ADMIN))),
    MUST_BE_LOGGED_OUT (Message.get(Message.ALREADY_LOGGED_IN));

    private String message;

    Permission(String message) {
        this.message = message;
    }

    public boolean assignedTo(Session session) {
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

    public String getMessage() {
        return this.message;
    }

    public static boolean hasAllPermissions(Permission[] requiredPermissions, Session session) throws AuthException {
        if (Arrays.stream(requiredPermissions).allMatch(perm -> perm.assignedTo(session))) {
            return true;
        }

        String exception = Arrays.stream(requiredPermissions)
                .filter(requiredPermission -> !requiredPermission.assignedTo(session))
                .map(Permission::getMessage)
                .collect(Collectors.joining(
                        Message.get(" " + AND.get() + " "), "", "."
                ));

        throw new AuthException(exception);

    }
}
