package task.interfaces;

/**
 * An interface for user interfaces.
 */
public interface IUserInterface {
    /**
     * Registers all the valid commands that are executable in the user interface.
     * @param commands The list of commands that are valid and executable.
     */
    void registerCommands(IExecutableCommand[] commands);

    /**
     * Inits and runs the whole user interface. Only stops, if a "quit" command is called.
     */
    void run();

    /**
     * Declares the user interface as running.
     */
    void start();

    /**
     * Declares the user interface as stopped / inactive.
     */
    void stop();
}