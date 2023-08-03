package me.syrym.assembler.hack.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CCommandTest {

    @Test
    void should_convert_c_command_without_jump_to_binary() {
        // given
        final var command = "D=D-M";

        // when
        final var result = new CCommand(command).toBinary();

        // then
        assertThat(result).isEqualTo("1111010011010000");
    }

    @Test
    void should_covert_c_command_with_jump_to_binary() {
        // given
        final var command = "D;JGT";

        // when
        final var result = new CCommand(command).toBinary();

        // then
        assertThat(result).isEqualTo("1110001100000001");
    }
}
