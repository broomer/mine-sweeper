package org.vmetl.minesweeper.game.controller;

import org.vmetl.minesweeper.game.board.Board;
import org.vmetl.minesweeper.game.board.Cell;
import org.vmetl.minesweeper.game.board.CellBoardState;
import org.vmetl.minesweeper.game.board.CellPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameController {

    private Board board;

    public void newGame(int dimension, int blackHolesNumber, Board board) {
        this.board = board;
    }

    // opens the cell, i.e. user has pressed it
    public void openCell(int x, int y) {
        board.changeCellState(new CellPosition(x, y), ActionType.OPEN);
    }

    // opens the random cell, for demoing purpose
    public CellPosition openRandomCell() {
        int dimension = board.getCells().length;
        int linearPosition = new Random().nextInt(dimension * dimension);
        CellPosition cellPosition = new CellPosition(linearPosition / dimension, linearPosition % dimension);
        board.changeCellState(cellPosition, ActionType.OPEN);

        return cellPosition;
    }

    // flag the cell as a Black Hole
    public void flagCell(int x, int y) {
        board.changeCellState(new CellPosition(x, y), ActionType.FLAG_HOLE);
    }

    public boolean isGameOver() {
        return board.hasExplodedCells();
    }

    public boolean isGameWon() {
        return board.allCellsOpen();
    }

    // show everything for ex. when the game is over
    public List<List<String>> getFullView() {
        return getView(GameController::getFinalCellRepresentation);
    }

    // show the current 'visual' state of the board
    public List<List<String>> getCurrentView() {
        return getView(GameController::getDiscoveredCellRepresentation);
    }

    private List<List<String>> getView(Function<Cell, String> getVisualCellRepresentation) {
        List<List<String>> view = new ArrayList<>();
        for (Cell[] row : board.getCells()) {
            List<String> rowArray = Arrays.stream(row).
                    map(getVisualCellRepresentation).collect(Collectors.toList());
            view.add(rowArray);
        }

        return view;
    }

    private static String getFinalCellRepresentation(Cell cell) {

        if (cell.getState() == CellBoardState.EXPLODED) return "X";

        if (cell.isBlackHole()) {
            return "B";
        } else {
            return Integer.toString(cell.getAdjacentHolesNumber());
        }
    }

    private static String getDiscoveredCellRepresentation(Cell cell) {

        switch (cell.getState()) {
            case FLAGGED:
                return "F";
            case UNKNOWN:
                return "U";
            default:
                return Integer.toString(cell.getAdjacentHolesNumber());
        }

    }

}
