package me.syrym.assembler.hack.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ACommandTest {

    @Test
    void should_convert_to_binary_valid_number_address() {
        // given
        var command = "1432";

        // when
        var aCommand = new ACommand(command);

        // then
        assertThat(aCommand.toBinary()).isEqualTo("10110011000");
    }
}

