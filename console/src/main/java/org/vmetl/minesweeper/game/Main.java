package org.vmetl.minesweeper.game;

import org.vmetl.minesweeper.game.board.CellPosition;
import org.vmetl.minesweeper.game.board.SimpleBoard;
import org.vmetl.minesweeper.game.controller.RandomSquareBoardBlackHolesGenerator;
import org.vmetl.minesweeper.game.controller.GameController;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: java -jar msw.jar <dimension> <black_holes_number>" + args.length);
            return;
        }

        int dimension = Integer.parseInt(args[0]);
        int blackHolesNumber = Integer.parseInt(args[1]);

        if (blackHolesNumber >= dimension * dimension) {
            System.out.println("ERROR: wrong parameters, too many black holes");
            return;
        }

        GameController gameController = new GameController();
        gameController.newGame(dimension, blackHolesNumber,
                new SimpleBoard(dimension, blackHolesNumber, new RandomSquareBoardBlackHolesGenerator()));

        CellPosition cellPosition = gameController.openRandomCell();
        System.out.println("Opening cell " + cellPosition);

        if (gameController.isGameOver()) {
            printBoard(gameController.getFullView());
            System.out.println("======= GAME OVER =======");
        } else {
            if (gameController.isGameWon()) {
                System.out.println("======= Congratulations, you won! =======");
            }
            printBoard(gameController.getCurrentView());
        }
    }

    private static void printBoard(List<List<String>> view) {
        System.out.println("Board State: \n\n");

        for (List<String> cells : view) {
            System.out.println(String.join(" ", cells));
        }

        System.out.println("\n\n");
    }

}