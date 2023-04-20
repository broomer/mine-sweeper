package org.vmetl.minesweeper.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @ParameterizedTest
    @MethodSource("getPositions")
    void testGetAdjacentPositions(CellPosition position, List<CellPosition> expectedAdjacentPositions) {
        Board board = new Board(5, emptySet());
        List<CellPosition> adjacentPositions = board.getAdjacentPositions(position);

        Assertions.assertEquals(expectedAdjacentPositions, adjacentPositions);

    }

//    * 0 0 0 *
//    0 0 0 0 0
//    0 0 * 0 0
//    0 0 0 0 0
//    * 0 0 0 *

    static Stream<Arguments> getPositions() {
        return Stream.of(
                Arguments.of(new CellPosition(0, 0),
                        List.of(new CellPosition(0, 1), new CellPosition(1, 0), new CellPosition(1, 1))),

                Arguments.of(new CellPosition(0, 4),
                        List.of(new CellPosition(0, 3), new CellPosition(1, 3), new CellPosition(1, 4))),

                Arguments.of(new CellPosition(4, 0),
                        List.of(new CellPosition(3, 0), new CellPosition(3, 1), new CellPosition(4, 1))),

                Arguments.of(new CellPosition(4, 4),
                        List.of(new CellPosition(3, 3), new CellPosition(3, 4), new CellPosition(4, 3))),

                Arguments.of(new CellPosition(2, 2),
                        List.of(new CellPosition(1, 1), new CellPosition(1, 2), new CellPosition(1, 3),
                                new CellPosition(2, 1), new CellPosition(2, 3),
                                new CellPosition(3, 1), new CellPosition(3, 2), new CellPosition(3, 3)))
        );
    }


//    0 0 0
//    B 0 B
//    B B B

    @Test
    void changeCellState() {
        Set<CellPosition> blackHoles = Set.of(
                new CellPosition(1, 0),
                new CellPosition(2, 0),
                new CellPosition(2, 1),
                new CellPosition(1, 2),
                new CellPosition(2, 2)
        );
        Board board = new Board(3, blackHoles);

        board.changeCellState(new CellPosition(1, 1), ActionType.OPEN);
        Cell[][] cells = board.getCells();
        Cell openCell = new Cell(new CellPosition(1, 1), false, 5);
        openCell.setState(CellBoardState.OPEN);

        Assertions.assertArrayEquals(new Cell[][]{
                        new Cell[]{
                                new Cell(new CellPosition(0, 0), false, 1),
                                new Cell(new CellPosition(0, 1), false, 2),
                                new Cell(new CellPosition(0, 2), false, 1),
                        },
                        new Cell[]{
                                new Cell(new CellPosition(1, 0), true, 2),
                                openCell,
                                new Cell(new CellPosition(1, 2), true, 2),
                        },
                        new Cell[]{
                                new Cell(new CellPosition(2, 0), true, 2),
                                new Cell(new CellPosition(2, 1), true, 4),
                                new Cell(new CellPosition(2, 2), true, 2),
                        },

                },
                cells);

        assertTrue(true);
    }

}