package org.vmetl.minesweeper.game.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.vmetl.minesweeper.game.board.CellPosition;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

// NOTE TO THE REVIEWER
// This test class does NOT contain all the use cases I would create if this was a production code.
// That would require more time than it makes sense (or I am willing to invest at the moment) for this kind of task.
// While not focusing solely on happy paths, it still misses out a lot
class RandomSquareBoardBlackHolesGeneratorTest {

    private SquareBoardBlackHolesGenerator blackHolesGenerator;

    @BeforeEach
    void setUp() {
        blackHolesGenerator = new RandomSquareBoardBlackHolesGenerator();
    }

    @Test
    void method_generateHoles_shouldReturnZero_when_zeroInput() {
        Set<CellPosition> cellPositions = blackHolesGenerator.generateBlackHoles(1, 0);

        assertThat("Incorrect size", cellPositions.isEmpty());
    }

    @Test
    void method_generateHoles_shouldReturnRequiredResult_when_correctInput() {
        Set<CellPosition> cellPositions = blackHolesGenerator.generateBlackHoles(10, 5);

        assertThat("Incorrect size", cellPositions.size() == 5);
    }

    @Test
    void method_generateHoles_shouldThrow_when_incorrectInput() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> blackHolesGenerator.generateBlackHoles(2, 6));

        assertThat(exception.getMessage(), is(equalTo("There cannot be more black holes than cells")));
    }


}