package me.syrym.assembler.hack;

import me.syrym.assembler.hack.parser.HackAssemblerParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one argument: path to the *.asm file");
        }
        final var parser = new HackAssemblerParser(args[0]);
        while (parser.hasMore()) {
            System.out.println(parser.next());
        }
    }
}