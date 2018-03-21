package task.userinterface;

import edu.kit.informatik.Terminal;
import task.constructs.database.Database;
import task.exceptions.AuthException;
import task.exceptions.CommandUndefinedException;
import task.exceptions.InvalidCallOfCommandException;
import task.exceptions.ParserException;
import task.interfaces.IUser;
import task.lang.Message;
import task.olympia.parser.OlympiaParser;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;
import task.interfaces.IUserInterface;
import task.userinterface.auth.AccountManager;
import task.userinterface.auth.LDAP;
import task.userinterface.auth.Session;
import task.userinterface.models.User;

import java.util.ArrayList;
import java.util.HashMap;

import static task.lang.Message.ALREADY_LOGGED_IN;

/**
 * The Command Line Interface used for handling all user interactions and outputs.
 * Here all the commands are registered and managed. All custom exceptions thrown in the stack are passed to this class
 * and the errors will be printed on the command line.
 */
public class CLI implements IUserInterface {
    private OlympiaParser parser;
    private Database userDatabase;
    private HashMap<String, IExecutableCommand> commandRegister;
    private boolean isRunning;
    private AccountManager accountManager;
    private Session session;

    /**
     * Creates a new Command Line Interface.
     * @param parser The injected parser that shall be used by the interface.
     */
    public CLI(OlympiaParser parser, Database userDatabase) {
        this.parser = parser;
        this.userDatabase = userDatabase;
        this.commandRegister = new HashMap<>();
        this.session = new Session();
        this.accountManager = new AccountManager(this.userDatabase, this.session);
    }

    @Override
    public void registerCommands(IExecutableCommand... commands) {
        for (IExecutableCommand command : commands) {
            this.registerCommand(command);
        }
    }

    /**
     * Registers a single command.
     * @param cmd The executable command that shall be registered.
     */
    private void registerCommand(IExecutableCommand cmd) {
        this.commandRegister.put(cmd.getSlug(), cmd);
    }

    @Override
    public void run() {
        this.start();

        while (this.isRunning) {
            try {
                ICommand inputCommand = this.parser.parseCommand(input());
                process(inputCommand);
            } catch (ParserException | InvalidCallOfCommandException exception) {
                this.outputError(exception.getMessage());
            }
        }
    }

    @Override
    public void start() {
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    public boolean registerUser(User newUser) throws AuthException {
        return this.accountManager.tryRegister(newUser);
    }

    public void login(String username, String password) throws AuthException {
        this.accountManager.tryLogin(username, password);
    }

    public void logout() throws AuthException {
        this.accountManager.tryLogout();
    }

    /**
     * Processes a command, which means passing the command data to the corresponding executable command class.
     * @param passedCommand The command that shall be executed.
     * @throws CommandUndefinedException Thrown if the command is not registered.
     * @throws InvalidCallOfCommandException Thrown if anything went wrong during the execution of the command.
     */
    private void process(ICommand passedCommand) throws InvalidCallOfCommandException {
        if (commandIsRegistered(passedCommand)) {
            // Lookup of the executable command that belongs to the passed command.
            IExecutableCommand correspondingCommand = this.commandRegister.get(passedCommand.getSlug());

            // Pass the passed command over to the corresponding executable command
            // for further validation and final execution.
            StringBuilder outputStream = new StringBuilder();
            correspondingCommand.tryToExecute(passedCommand, outputStream);

            // Show all messages that have been generated within the command
            flushOutput(outputStream);
        } else {
            throw new InvalidCallOfCommandException("the command \"" + passedCommand.getSlug() + "\" is not defined.");
        }
    }

    /**
     * Checks whether a particular command (a command slug respectively) is registered.
     * @param command The command that should be searched for.
     * @return Returns true if the command is registered.
     */
    private boolean commandIsRegistered(ICommand command) {
        return this.commandRegister.containsKey(command.getSlug());
    }

    /**
     * Returns the parser that belongs to the Interface.
     * @return The parser of this interface.
     */
    public OlympiaParser getParser() {
        return this.parser;
    }

    public Session getSession() {
        return session;
    }

    /**
     * Used for displaying all the messages that have been generated within a command.
     * @param outputStream The output stream that has been passed to the command before.
     */
    private void flushOutput(StringBuilder outputStream) {
        if (!outputStream.toString().equals("")) {
            output(outputStream.toString());
        }
    }

    /**
     * Used for outputting messages via the command line.
     * @param message The message that shall be printed.
     */
    private void output(String message) {
        Terminal.printLine(message);
    }

    /**
     * Used for outputting errors via the command line.
     * @param message The error that shall be printed.
     */
    public void outputError(String message) {
        Terminal.printError(message);
    }

    /**
     * Used for reading input via the command line.
     * @return Returns the String value that has been inputted.
     */
    private String input() {
        return Terminal.readLine();
    }
}