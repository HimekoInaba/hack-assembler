package me.syrym.assembler.hack.parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class HackAssemblerParserTest {

    @Test
    public void should_parse_valid_asm_file_ignoring_blank_lines_and_comments() throws IOException {
        // given
        var parser = new HackAssemblerParser("src/test/resources/examples/add/Add.asm");

        // when
        var result = new ArrayList<>();
        while (parser.hasMore()) {
            result.add(parser.next());
        }

        // then
        var expected = Files.readAllLines(Path.of("src/test/resources/examples/add/Add.asm.parsed"));
        assertThat(result).containsExactly(expected.toArray(String[]::new));
    }
}
