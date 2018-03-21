package task.interfaces;

import task.exceptions.AuthException;
import task.userinterface.auth.Permission;
import task.userinterface.auth.Session;


public interface IRestrictedCommand {
    default boolean checkPermissions(Session session) throws AuthException {
        return Permission.hasAllPermissions(getPermissionFlags(), session);
    }

    Permission[] getPermissionFlags();
}