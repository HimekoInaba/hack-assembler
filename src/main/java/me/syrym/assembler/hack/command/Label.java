package me.syrym.assembler.hack.command;

public class Label extends Command {

    public Label(String command) {
        super(command);
    }

    public String getSymbol() {
        return this.command.substring(1, this.command.length() - 1);
    }

    @Override
    public String toBinary() {
        throw new UnsupportedOperationException("Lablel command should not be converted to binary");
    }
}
