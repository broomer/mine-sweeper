package org.vmetl.minesweeper.game.board;

import org.vmetl.minesweeper.game.controller.ActionType;
import org.vmetl.minesweeper.game.controller.SquareBoardBlackHolesGenerator;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableSet;

public final class SimpleBoard implements Board {

    private final Cell[][] cells;
    private final int dimension;
    private final Set<CellPosition> blackHoles;

    public SimpleBoard(int dimension, int blackHolesNumber, SquareBoardBlackHolesGenerator blackHolesGenerator) {

        if (blackHolesNumber >= dimension * dimension) {
            throw new IllegalStateException("Incorrect board parameters, too many black holes");
        }

        this.cells = new Cell[dimension][dimension];
        this.dimension = dimension;
        this.blackHoles = unmodifiableSet(blackHolesGenerator.generateBlackHoles(dimension, blackHolesNumber));

        initBoard(blackHoles);
    }

    void initBoard(Set<CellPosition> blackHoles) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                CellPosition cellPosition = new CellPosition(i, j);

                boolean isBlackHole = blackHoles.contains(cellPosition);
                cells[i][j] = new Cell(cellPosition, isBlackHole, getAdjacentHolesNumber(blackHoles, cellPosition));
            }
        }
    }

    private int getAdjacentHolesNumber(Set<CellPosition> blackHoles, CellPosition cellPosition) {
        return ((Long) getAdjacentPositions(cellPosition).stream().
                filter(blackHoles::contains).count()).intValue();
    }

    public Cell getCellAt(CellPosition cellPosition) {

        if (!withinBoundaries(cellPosition)) {
            throw new IllegalStateException(
                    "You've got a nasty error, even developers don't know what is happening! cell position: " + cellPosition);
        }

        return cells[cellPosition.getX()][cellPosition.getY()];
    }

    @Override
    public void changeCellState(CellPosition cellPosition, ActionType action) {
        Cell cell = getCellAt(cellPosition);

        if (cell.isBlackHole() && action == ActionType.OPEN) {
            cell.setState(CellBoardState.EXPLODED);
        }

        openCell(cell);
    }

    @Override
    public boolean hasExplodedCells() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getState() == CellBoardState.EXPLODED)
                    return true;
            }
        }

        return false;
    }

    @Override
    public boolean allCellsOpen() {
        int totalOpened = 0;
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getState() == CellBoardState.OPEN)
                    totalOpened++;
            }
        }

        return dimension * dimension - blackHoles.size() == totalOpened;
    }

    private void openCell(Cell cell) {

        if (cell.getState() == CellBoardState.UNKNOWN || cell.getState() == CellBoardState.FLAGGED) {
            Collection<CellPosition> adjacentPositions = getAdjacentPositions(cell.getPosition());

            List<Cell> adjacentCells =
                    adjacentPositions.stream().map(this::getCellAt).collect(Collectors.toList());

            cell.setState(CellBoardState.OPEN);
            if (cell.getAdjacentHolesNumber() == 0) {
                for (Cell adjacentCell : adjacentCells) {
                    openCell(adjacentCell);
                }
            }
        }
    }

    @Override
    public Cell[][] getCells() {
        return Arrays.stream(cells)
                .map(row -> Arrays.stream(row).map(Cell::of).toArray(Cell[]::new))
                .toArray(Cell[][]::new);
    }

    List<CellPosition> getAdjacentPositions(CellPosition position) {
        Collection<CellPosition> adjacents = new ArrayList<>();
        adjacents.add(new CellPosition(position.getX() - 1, position.getY() - 1));
        adjacents.add(new CellPosition(position.getX() - 1, position.getY()));
        adjacents.add(new CellPosition(position.getX() - 1, position.getY() + 1));
        adjacents.add(new CellPosition(position.getX(), position.getY() - 1));
        adjacents.add(new CellPosition(position.getX(), position.getY() + 1));
        adjacents.add(new CellPosition(position.getX() + 1, position.getY() - 1));
        adjacents.add(new CellPosition(position.getX() + 1, position.getY()));
        adjacents.add(new CellPosition(position.getX() + 1, position.getY() + 1));

        return adjacents.stream().filter(this::withinBoundaries).collect(Collectors.toList());
    }

    boolean withinBoundaries(CellPosition position) {
        return position.getX() >= 0 && position.getX() < dimension
                && position.getY() >= 0 && position.getY() < dimension;
    }

}
