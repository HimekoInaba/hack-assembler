package me.syrym.assembler.hack;

import me.syrym.assembler.hack.command.ACommand;
import me.syrym.assembler.hack.command.CCommand;
import me.syrym.assembler.hack.command.Label;
import me.syrym.assembler.hack.parser.HackAssemblerParser;

import java.io.IOException;

import static me.syrym.assembler.hack.command.CommandType.commandType;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one argument: path to the *.asm file");
        }

        final var inputFilePath = args[0];
        final var parser = new HackAssemblerParser(inputFilePath);
        while (parser.hasMore()) {
            final var currentLine = parser.next();
            final var commandType = commandType(currentLine);
            final var command = switch (commandType) {
                case A_COMMAND -> new ACommand(currentLine);
                case C_COMMAND -> new CCommand(currentLine);
                case LABEL -> new Label(currentLine);
            };
            System.out.println(command.toBinary());
        }
    }
}