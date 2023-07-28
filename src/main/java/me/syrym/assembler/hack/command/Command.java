package me.syrym.assembler.hack.command;

public abstract class Command {

    protected final String command;

    protected Command(String command) {
        validate(command);
        this.command = command;
    }

    public abstract String toBinary();

    private void validate(String command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        if (command.isBlank()) {
            throw new IllegalArgumentException("Command cannot be blank");
        }
    }

}
