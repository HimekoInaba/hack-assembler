package me.syrym.assembler.hack;

import me.syrym.assembler.hack.command.ACommand;
import me.syrym.assembler.hack.command.CCommand;
import me.syrym.assembler.hack.command.CommandType;
import me.syrym.assembler.hack.command.Label;
import me.syrym.assembler.hack.parser.HackAssemblerParser;
import me.syrym.assembler.hack.parser.HackAssemblerWriter;

import java.io.IOException;

import static me.syrym.assembler.hack.command.CommandType.commandType;

public class Main {

    private static final SymbolicTable symbolTable = new SymbolicTable();

    private final HackAssemblerParser parser;
    private final HackAssemblerWriter writer;

    private Main(String inputFilePath) throws IOException {
        this.parser = new HackAssemblerParser(inputFilePath);

        final var outputFilePath = inputFilePath.replace(".asm", ".hack");
        this.writer = new HackAssemblerWriter(outputFilePath);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one argument: path to the *.asm file");
        }

        final var assembler = new Main(args[0]);
        assembler.populateLabels();
        assembler.populateVariables();
        assembler.translate();
    }

    private void populateLabels() {
        var programLine = 0;
        while (parser.hasMore()) {
            final var currentLine = parser.next();
            final var commandType = commandType(currentLine);

            if (commandType == CommandType.LABEL) {
                final var label = new Label(currentLine);
                symbolTable.addEntryIfAbsent(label.getSymbol(), programLine);
            } else {
                programLine++;
            }
        }
    }

    private void populateVariables() {
        parser.reset();
        while (parser.hasMore()) {
            final var currentLine = parser.next();
            final var commandType = commandType(currentLine);

            if (commandType == CommandType.A_COMMAND) {
                final var aCommand = new ACommand(currentLine);
                if (aCommand.isSymbol()) {
                    final var symbol = aCommand.getSymbol();
                    if (!symbolTable.contains(symbol)) {
                        symbolTable.addVariable(symbol);
                    }
                }
            }
        }
    }

    private void translate() throws IOException {
        parser.reset();
        while (parser.hasMore()) {
            final var currentLine = parser.next();
            final var commandType = commandType(currentLine);

            final var command = switch (commandType) {
                case A_COMMAND -> {
                    final var aCommand = new ACommand(currentLine);
                    if (aCommand.isSymbol()) {
                        final var symbol = aCommand.getSymbol();
                        final var address = symbolTable.getAddress(symbol);
                        yield new ACommand("@" + address);
                    } else {
                        yield aCommand;
                    }
                }
                case C_COMMAND -> new CCommand(currentLine);
                case LABEL -> {
                    final var label = new Label(currentLine);
                    symbolTable.addEntryIfAbsent(label.getSymbol(), parser.getCurrentLine());
                    yield null;
                }
            };

            if (command == null) {
                continue;
            }

            writer.write(command.toBinary());
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
}