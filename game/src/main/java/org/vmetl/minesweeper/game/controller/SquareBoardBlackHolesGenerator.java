package org.vmetl.minesweeper.game.controller;

import org.vmetl.minesweeper.game.board.CellPosition;

import java.util.Set;

public interface SquareBoardBlackHolesGenerator {
    Set<CellPosition> generateBlackHoles(int dimension, int blackHolesNumber);
}
