package task.constructs.commands;

import task.interfaces.ICommand;

/**
 * This classed is used only for holding command data. These Commands are not yet executable, but at least have the
 * structure of a Command. These Objects will be passed to the corresponding class that implements IExecutableCommand,
 * where the set of instructions which is applicable to the command is held.
 */
public class Command implements ICommand {
    private String slug;
    private String[] args;

    /**
     * Instantiates a new Command Object.
     * @param slug The slug the command has.
     * @param args The corresponding command arguments.
     */
    public Command(String slug, String[] args) {
        this.slug = slug;
        this.args = args;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }

    @Override
    public String[] getArgs() {
        return this.args;
    }

    @Override
    public String getArg(int index) {
        return (this.args != null && this.args.length > index) ? this.args[index] : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Command)) {
            return false;
        }

        Command command = (Command) obj;

        return this.slug.equals(command.getSlug()) && this.args == command.getArgs();
    }
}