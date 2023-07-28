package me.syrym.assembler.hack.command;

public enum CommandType {
    A_COMMAND,
    C_COMMAND,
    LABEL;

    public static CommandType commandType(String str) {
        if (str.startsWith("@")) {
            return A_COMMAND;
        } else if (str.startsWith("(") && str.endsWith(")")) {
            return LABEL;
        } else {
            return C_COMMAND;
        }
    }
}
