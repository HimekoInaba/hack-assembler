package me.syrym.assembler.hack.command;

import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public class CCommand extends Command {

    private static final Map<String, String> COMPUTATIONS_TABLE = Map.ofEntries(
            entry("0", "0101010"),
            entry("1", "0111111"),
            entry("-1", "0111010"),
            entry("D", "0001100"),
            entry("A", "0110000"),
            entry("!D", "0001101"),
            entry("!A", "0110001"),
            entry("-D", "0001111"),
            entry("-A", "0110011"),
            entry("D+1", "0011111"),
            entry("A+1", "0110111"),
            entry("D-1", "0001110"),
            entry("A-1", "0110010"),
            entry("D+A", "0000010"),
            entry("D-A", "0010011"),
            entry("A-D", "0000111"),
            entry("D&A", "0000000"),
            entry("D|A", "0010101"),
            entry("M", "1110000"),
            entry("!M", "1110001"),
            entry("-M", "1110011"),
            entry("M+1", "1110111"),
            entry("M-1", "1110010"),
            entry("D+M", "1000010"),
            entry("D-M", "1010011"),
            entry("M-D", "1000111"),
            entry("D&M", "1000000"),
            entry("D|M", "1010101")
    );

    private static final Map<String, String> DESTINATIONS_TABLE = Map.ofEntries(
            entry("null", "000"),
            entry("M", "001"),
            entry("D", "010"),
            entry("MD", "011"),
            entry("A", "100"),
            entry("AM", "101"),
            entry("AD", "110"),
            entry("AMD", "111")
    );

    private static final Map<String, String> JUMP_TABLE = Map.ofEntries(
            entry("null", "000"),
            entry("JGT", "001"),
            entry("JEQ", "010"),
            entry("JGE", "011"),
            entry("JLT", "100"),
            entry("JNE", "101"),
            entry("JLE", "110"),
            entry("JMP", "111")
    );

    private final String[] destAndComp;
    private final String[] compAndJump;

    public CCommand(String command) {
        super(command);
        this.destAndComp = this.command.split("=");
        this.compAndJump = this.command.split(";");
    }

    @Override
    public String toBinary() {
        final var builder = new StringBuilder("111");
        builder.append(comp().map(COMPUTATIONS_TABLE::get).orElse("0000000"));
        builder.append(dest().map(DESTINATIONS_TABLE::get).orElse("000"));
        builder.append(jump().map(JUMP_TABLE::get).orElse("000"));
        return builder.toString();
    }

    private Optional<String> dest() {
        if (this.command.contains("=")) {
            return Optional.of(destAndComp[0]);
        }
        return Optional.empty();
    }

    private Optional<String> comp() {
        if (this.command.contains("=")) {
            return Optional.of(destAndComp[1]);
        }
        return Optional.of(compAndJump[0]);
    }

    private Optional<String> jump() {
        if (this.command.contains(";")) {
            return Optional.of(compAndJump[1]);
        }
        return Optional.empty();
    }
}
