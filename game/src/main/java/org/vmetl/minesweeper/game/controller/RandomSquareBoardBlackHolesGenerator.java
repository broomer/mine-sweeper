package org.vmetl.minesweeper.game.controller;

import org.vmetl.minesweeper.game.board.CellPosition;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomSquareBoardBlackHolesGenerator implements SquareBoardBlackHolesGenerator {
    @Override
    public Set<CellPosition> generateBlackHoles(int dimension, int blackHolesNumber) {
        if (dimension * dimension <= blackHolesNumber) {
            throw new IllegalArgumentException("There cannot be more black holes than cells");
        }
        Set<CellPosition> blackHoles = new HashSet<>();
        Random random = new Random();
        while (blackHoles.size() < blackHolesNumber) {
            int linearPosition = random.nextInt(dimension * dimension);
            blackHoles.add(new CellPosition(linearPosition / dimension, linearPosition % dimension));
        }

        return blackHoles;
    }
}
