package me.syrym.assembler.hack.command;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toBinaryString;

public class ACommand extends Command {

    public ACommand(String command) {
        super(command);

    }

    @Override
    public String toBinary() {
        final var address = this.command.substring(1, this.command.length() - 1);
        if (isNumber(address)) {
            return String.format(
                    "%16s",
                    toBinaryString(parseInt(address))).replace(' ', '0'
            );
        }
        return "invalid";
    }

    private boolean isNumber(String str) {
        try {
            parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
