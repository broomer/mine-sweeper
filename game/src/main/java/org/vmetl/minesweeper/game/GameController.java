package org.vmetl.minesweeper.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameController {

    private Board board;

    public void newGame(int dimension, int blackHolesNumber) {
        board = new Board(dimension, blackHolesNumber, new BlackHolesGenerator());
    }

    public void openCell(int x, int y) {
        board.changeCellState(new CellPosition(x, y), ActionType.OPEN);
    }

    public CellPosition openRandomCell() {
        int dimension = board.getDimension();
        int linearPosition = new Random().nextInt(dimension * dimension);
        CellPosition cellPosition = new CellPosition(linearPosition / dimension, linearPosition % dimension);
        board.changeCellState(cellPosition, ActionType.OPEN);

        return cellPosition;
    }

    public void markCell(int x, int y) {
        board.changeCellState(new CellPosition(x, y), ActionType.FLAG_HOLE);
    }

    public boolean gameOver() {
        return board.hasExplodedCells();
    }

    public boolean gameWon() {
        return board.allCellsOpen();
    }

    public List<List<String>> getFullView() {
        return getOpenView(GameController::getFinalCellRepresentation);
    }

    public List<List<String>> getOpenView() {
        return getOpenView(GameController::getDiscoveredCellRepresentation);
    }

    private List<List<String>> getOpenView(Function<Cell, String> getVisualCellRepresentation) {
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
