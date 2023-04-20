package org.vmetl.minesweeper.game;


import java.util.Objects;

public final class Cell {

    private final CellPosition position;
    private final boolean isBlackHole;

    private final int adjacentHolesNumber;
    private CellBoardState state;


    public Cell(CellPosition position, boolean isBlackHole, int adjacentHolesNumber) {
        this.isBlackHole = isBlackHole;
        this.adjacentHolesNumber = adjacentHolesNumber;
        this.position = position;
        this.state = CellBoardState.UNKNOWN;
    }

    public void setState(CellBoardState state) {
        this.state = state;
    }

    public int getAdjacentHolesNumber() {
        return adjacentHolesNumber;
    }

    public CellPosition getPosition() {
        return position;
    }

    public CellBoardState getState() {
        return state;
    }

    public boolean isBlackHole() {
        return isBlackHole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return isBlackHole == cell.isBlackHole && adjacentHolesNumber == cell.adjacentHolesNumber && position.equals(cell.position) && state == cell.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, isBlackHole, adjacentHolesNumber, state);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "position=" + position +
                ", isBlackHole=" + isBlackHole +
                ", adjacentHolesNumber=" + adjacentHolesNumber +
                ", state=" + state +
                '}';
    }
}
