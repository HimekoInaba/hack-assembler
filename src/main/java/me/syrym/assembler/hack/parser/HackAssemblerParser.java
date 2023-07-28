package me.syrym.assembler.hack.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class HackAssemblerParser {

    private final List<String> lines;
    private final int lastLineNumber;

    private int currentLine = 0;

    public HackAssemblerParser(String path) throws IOException {
        validatePath(path);
        final var file = new File(path);
        validateFile(file);
        this.lines = Files.readAllLines(file.toPath());
        this.lastLineNumber = this.lines.size();
    }

    public boolean hasMore() {
        return this.currentLine < this.lastLineNumber;
    }

    public String next() {
        return getAt(this.currentLine);
    }

    public String getAt(int lineNumber) {
        if (lineNumber < 0) {
            throw new IllegalArgumentException("Line number must be positive");
        }
        if (lineNumber >= this.lastLineNumber) {
            throw new IllegalArgumentException("Reached end of the file");
        }
        this.currentLine = lineNumber;

        final var line = this.lines.get(currentLine++);
        if (line.isBlank() || line.startsWith("//")) {
            return getAt(currentLine);
        }

        return line;
    }

    private void validatePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path to the *.asm file cannot be null or empty");
        }
    }

    private void validateFile(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file.getAbsolutePath() + " does not exist");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Path " + file.getAbsolutePath() + " is not a file");
        }
        if (!file.getName().endsWith(".asm")) {
            throw new IllegalArgumentException("File " + file.getAbsolutePath() + " is not an *.asm file");
        }
    }

}
