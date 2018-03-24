package task.constructs.commands;

import java.util.Arrays;

/**
 * This class is the blueprint for a command signature. A signature specifies how a valid command call looks like.
 * In the command signature, the command slug and the command arguments are encoded.
 */
public class CommandSignature {
    private String commandSignature;
    private String slug;
    private Argument[] args;

    /**
     * Instantiates a new command signature.
     *
     * @param slug the slug of the command.
     * @param arguments the argument list that belongs to the signature.
     */
    public CommandSignature(String slug, Argument... arguments) {
        this.slug = slug;
        this.args = arguments;
        this.commandSignature = this.serialize();
    }

    private String serialize() {
        StringBuilder commandSignature = new StringBuilder();
        commandSignature.append(this.slug + " ");
        for (int i = 0; i < this.args.length; i++) {
            commandSignature.append(this.args[i].getArgName()); //+ ":" + this.args[i].getArgType().toString());
            commandSignature.append(i < this.args.length - 1 ? ";" : "");
        }

        return commandSignature.toString();
    }

    /**
     * Reutns the actual signature.
     *
     * @return The actual signature.
     */
    public String getCommandSignature() {
        return commandSignature;
    }

    /**
     * Gets the slug of the command signature.
     *
     * @return Returns the slug.
     */
    public String getSlug() {
        return this.slug;
    }

    /**
     * Gets all the arguments in the command signature, split into argument name and datatype.
     *
     * @return Returns the argument structure as a two dimensional array.
     */
    public Argument[] getArgs() {
        return this.args;
    }

    /**
     * Checks if there are any arguments in the signature.
     *
     * @return Returns true if there are arguments.
     */
    public boolean hasArguments() {
        return getArgCount() > 0;
    }

    /**
     * Gets the number of arguments that belong to this signature.
     *
     * @return Returns the number of arguments that belong to this command signature.
     */
    public int getArgCount() {
        return (this.args == null) ? 0 : this.args.length;
    }

    /**
     * Gets the datatype of the argument at a database position in the argument list.
     *
     * @param index the index of the argument of which the datatype shall be returned.
     * @return The datatype of the argument.
     */
    public Datatype getArgType(int index) {
        return hasArguments() ? this.args[index].getArgType() : null;
    }

    /**
     * Gets all the datatypes of the arguments as an array.
     *
     * @return Returns an array of all the argument types of the command signature.
     */
    public Datatype[] getArgTypes() {
        if (hasArguments()) {
            Datatype[] argTypesList = new Datatype[this.args.length];

            for (int i = 0; i < argTypesList.length; i++) {
                argTypesList[i] = getArgType(i);
            }

            return argTypesList;
        }

        return null;
    }

    /**
     * Used to getFormatted the name of the actual parameter.
     *
     * @param index the index of the parameter name you want to getFormatted.
     * @return Returns the name of the parameter at the given index.
     */
    public String getArgName(int index) {
        if (hasArguments()) {
            String paramName = this.args[index].getArgName();

            if (paramName == null) {
                return "argument on position " + index;
            }

            return paramName;
        }

        return null;
    }

    /**
     * Gets the human readable argument names.
     *
     * @return Returns an array of all the argument names corresponding to this command signature.
     */
    public String[] getArgNames() {
        if (hasArguments()) {
            String[] argNameList = new String[this.args.length];

            for (int i = 0; i < argNameList.length; i++) {
                argNameList[i] = getArgName(i);
            }

            return argNameList;
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CommandSignature)) {
            return false;
        }

        CommandSignature sign = (CommandSignature) obj;

        return this.slug.equals(sign.getSlug())
                && Arrays.equals(this.args, sign.getArgs())
                && this.commandSignature.equals(sign.getCommandSignature());
    }
}