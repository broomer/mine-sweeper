package org.vmetl.minesweeper.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlackHolesGenerator {
    public Set<CellPosition> generateBlackHoles(int dimension, int blackHolesNumber) {
        Set<CellPosition> blackHoles = new HashSet<>();
        Random random = new Random();
        while (blackHoles.size() < blackHolesNumber) {
            int linearPosition = random.nextInt(dimension * dimension);
            blackHoles.add(new CellPosition(linearPosition / dimension, linearPosition % dimension));
        }

        return blackHoles;
    }
}
