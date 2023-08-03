package me.syrym.assembler.hack.parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HackAssemblerWriter {

    private final BufferedWriter writer;

    public HackAssemblerWriter(String path) throws IOException {
        validatePath(path);
        this.writer = Files.newBufferedWriter(Path.of(path), StandardCharsets.UTF_8);
    }

    public void write(String line) throws IOException {
        this.writer.write(line);
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }

    private void validatePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path to the *.hack file cannot be null or empty");
        }
    }
}
