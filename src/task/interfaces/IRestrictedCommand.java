package task.interfaces;

import task.exceptions.AuthException;
import task.userinterface.auth.Permission;
import task.userinterface.auth.Session;


public interface IRestrictedCommand extends IExecutableCommand {
    /**
     * Checks whether all the rights are assigned to the session.
     *
     * @param session The session.
     * @return - returns true if session has all rights.
     * @throws AuthException thrown if the right are not assigned.
     */
    default boolean checkPermissions(Session session) throws AuthException {
        return Permission.requirePermissions(getPermissionFlags(), session);
    }

    /**
     * @return Returns all permission flags.
     */
    Permission[] getPermissionFlags();
}