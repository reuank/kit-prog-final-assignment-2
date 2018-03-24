package task.userinterface;

import edu.kit.informatik.Terminal;
import task.constructs.database.Database;
import task.exceptions.AuthException;
import task.exceptions.CommandUndefinedException;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ParserException;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;
import task.interfaces.IUserInterface;
import task.lang.Message;
import task.userinterface.auth.AccountManager;
import task.userinterface.auth.Session;
import task.userinterface.models.User;
import task.userinterface.parser.UserIntefaceParser;
import task.userinterface.validation.InputValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Command Line Interface used for handling all user interactions and outputs.
 * Here all the commands are registered and managed. All custom exceptions thrown in the stack are passed to this class
 * and the errors will be printed on the command line.
 */
public class CLI implements IUserInterface {
    private UserIntefaceParser uiParser;
    private InputValidator inputValidator;
    private Database userDatabase;
    private HashMap<String, IExecutableCommand> commandRegister;
    private boolean isRunning;
    private AccountManager accountManager;
    private Session session;

    /**
     * Creates a new Command Line Interface.
     * @param uiParser The injected parser that shall be used by the interface.
     * @param inputValidator the input validator.
     * @param userDatabase the user database.
     */
    public CLI(UserIntefaceParser uiParser, InputValidator inputValidator, Database userDatabase) {
        this.uiParser = uiParser;
        this.inputValidator = inputValidator;
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
                ICommand inputCommand = this.uiParser.parseCommand(input());
                process(inputCommand);
            } catch (CommandUndefinedException | IllegalCallOfCommandException exception) {
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

    /**
     * Tries to register a new user.
     * @param newUser the new user.
     * @return returns true if the registration was successful.
     * @throws AuthException thrown if the registration failed, thus the user already exists.
     */
    public boolean tryRegisterUser(User newUser) throws AuthException {
        return this.accountManager.tryRegister(newUser);
    }

    /**
     * Tries to login a user.
     * @param username the username.
     * @param password the password.
     * @throws AuthException thrown if the credentials are wrong.
     */
    public void tryLogin(String username, String password) throws AuthException {
        this.accountManager.tryLogin(username, password);
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        this.accountManager.logout();
    }

    /**
     * Processes a command, which means passing the command data to the corresponding executable command class.
     * @param passedCommand The command that shall be executed.
     * @throws CommandUndefinedException Thrown if the command is not registered.
     * @throws IllegalCallOfCommandException Thrown if anything went wrong during the execution of the command.
     */
    private void process(ICommand passedCommand) throws IllegalCallOfCommandException, CommandUndefinedException {
        if (commandIsRegistered(passedCommand)) {
            // Lookup of the executable command that belongs to the passed command.
            IExecutableCommand correspondingCommand = this.commandRegister.get(passedCommand.getSlug());

            // Pass the passed command over to the corresponding executable command
            // for further validation and final execution.
            List<String> outputStream = new ArrayList<>();
            correspondingCommand.tryToExecute(passedCommand, outputStream);

            // Show all messages that have been generated within the command
            flushOutput(outputStream);
        } else {
            throw new CommandUndefinedException(
                    Message.get(Message.COMMAND_$STRING$_UNDEFINED, passedCommand.getSlug())
            );
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
     * @return - returns the inputValidator.
     **/
    public InputValidator getInputValidator() {
        return inputValidator;
    }

    /**
     * Returns the parser that belongs to the Interface.
     * @return The parser of this interface.
     */
    public UserIntefaceParser getUiParser() {
        return this.uiParser;
    }

    /**
     * @return - returns the session
     **/
    public Session getSession() {
        return session;
    }

    /**
     * Used for displaying all the messages that have been generated within a command.
     * @param outputStream The output stream that has been passed to the command before.
     */
    private void flushOutput(List<String> outputStream) {
        if (!outputStream.isEmpty()) {
            output(outputStream.stream().collect(Collectors.joining("\n")));
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