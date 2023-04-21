package org.vmetl.minesweeper.game.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vmetl.minesweeper.game.controller.ActionType;
import org.vmetl.minesweeper.game.controller.SquareBoardBlackHolesGenerator;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


// NOTE TO THE REVIEWER
// This test class does NOT contain all the use cases I would create if this was a production code.
// That would require more time than it makes sense (or I am willing to invest at the moment) for this kind of task.
// While not focusing solely on happy paths, it still misses out a lot:
class SimpleBoardTest {

    private SquareBoardBlackHolesGenerator blackHolesGenerator;

    @BeforeEach
    void setUp() {
        blackHolesGenerator = mock(SquareBoardBlackHolesGenerator.class);
    }

    @ParameterizedTest
    @MethodSource("getPositions")
    void method_etAdjacentPositions_shouldReturnCorrectPositions(CellPosition position, List<CellPosition> expectedAdjacentPositions) {
        when(blackHolesGenerator.generateBlackHoles(anyInt(), anyInt())).thenReturn(Set.of());

        SimpleBoard board = new SimpleBoard(5, 0, blackHolesGenerator);
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

    @Test
    void method_changeCellState_shouldChangeStateCorrectly() {

        when(blackHolesGenerator.generateBlackHoles(anyInt(), anyInt())).thenReturn(getBlackHolesPositions());

        SimpleBoard board = new SimpleBoard(3, 5, blackHolesGenerator);

        board.changeCellState(new CellPosition(1, 1), ActionType.OPEN);
        Cell[][] cells = board.getCells();
        Cell openCell = new Cell(new CellPosition(1, 1), false, 5);
        openCell.setState(CellBoardState.OPEN);

        assertArrayEquals(new Cell[][]{
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
    }

    @Test
    void method_getCells_should_returnCopy() {
        when(blackHolesGenerator.generateBlackHoles(anyInt(), anyInt())).thenReturn(Set.of());

        Board board = new SimpleBoard(3, 0, blackHolesGenerator);
        Cell[][] cells = board.getCells();

        assertThat(cells, equalTo(board.getCells()));

        //modify the view
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.setState(CellBoardState.FLAGGED);
            }
        }

        // assert the underlying data has not changed, i.e. modified view is no longer equal to the fresh copy of it
        assertThat(cells, not(equalTo(board.getCells())));
    }

    //    0 0 0
//    B 0 B
//    B B B
    private static Set<CellPosition> getBlackHolesPositions() {
        return Set.of(
                new CellPosition(1, 0),
                new CellPosition(2, 0),
                new CellPosition(2, 1),
                new CellPosition(1, 2),
                new CellPosition(2, 2)
        );
    }
}