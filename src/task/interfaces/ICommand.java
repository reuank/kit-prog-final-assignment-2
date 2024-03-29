package task.interfaces;

/**
 * An interface for commands.
 */
public interface ICommand {
    /**
     * Used to getOwnFormatted the slug of the command.
     * @return Returns the command slug.
     */
    String getSlug();

    /**
     * Used to getOwnFormatted the arguments of the command.
     * @return Returns the command arguments.
     */
    String[] getArgs();

    /**
     * Used to getOwnFormatted a database command argument.
     * @param index The id if the argument.
     * @return Return the argument with the given id.
     */
    String getArg(int index);
}
