package task.interfaces;

import task.exceptions.*;
import task.userinterface.auth.Permission;
import task.userinterface.auth.Session;

import java.util.List;

/**
 * An interface for restricted commands.
 */
public interface IRestrictedCommand extends IExecutableCommand {
    @Override
    default void executeCommand(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        this.checkPermissions(this.getSession());
        this.restrictedExecution(command, outputStream);
    }

    /**
     * Checks whether all the rights are assigned to the session.
     *
     * @param session The session.
     * @throws AuthException thrown if the right are not assigned.
     */
    default void checkPermissions(Session session) throws AuthException {
        Permission.requirePermissions(getPermissionFlags(), session);
    }

    /**
     * @return Returns all permission flags.
     */
    Permission[] getPermissionFlags();

    /**
     * @param cmd the command
     * @param stream the output stream.
     * @throws IllegalCallOfCommandException thrown if the execution went wrong.
     */
    void restrictedExecution(ICommand cmd, List<String> stream) throws IllegalCallOfCommandException;

    /**
     * @return gets the session object, so that the permissions can be checked.
     */
    Session getSession();
}
