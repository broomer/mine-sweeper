package org.vmetl.minesweeper.game.board;

import org.vmetl.minesweeper.game.controller.ActionType;

/**
 * A generic Board representation, w/o assumptions about it's size, dimensions etc.
 */
public interface Board {
    /**
     * Interact with the cell, i.e. open, it, or flag a hole
     *
     * @param cellPosition - position of the cell
     * @param action - action type
     */
    void changeCellState(CellPosition cellPosition, ActionType action);

    /**
     * Indicates a loosing situation, i.e. the user interacted with the hole by opening it
     *
     * @return true if the board contains a cell which exploded
     */
    boolean hasExplodedCells();

    /**
     * Indicates whether all cells have been 'logically opened' (auto- or as a result of interaction with the cell),
     * i.e. it is possible to deduct the winning situation from this state
     *
     * @return true if all non-hole cells have been opened, false otherwise
     */
    boolean allCellsOpen();

    /**
     * Get a copy view of the board content. Changes to this view are not reflected in the board state.
     * @return a copy of the cells matrix
     */
    Cell[][] getCells();
}
