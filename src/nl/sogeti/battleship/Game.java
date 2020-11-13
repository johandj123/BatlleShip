package nl.sogeti.battleship;

import java.util.Scanner;

public class Game {
    private Board myBoard = new Board();

    private Board opponentBoard = new Board();

    private Scanner scanner = new Scanner(System.in);

    private boolean isMyTurn = true;

    public void play() {
        while (!myBoard.isGameLost() && !opponentBoard.isGameLost()) {
            if (isMyTurn) {
                printBoards();
                myTurn();
            } else {
                opponentTurn();
            }
            isMyTurn = !isMyTurn;
        }
    }

    private void printBoards() {
        myBoard.boardToText(false).forEach(System.out::println);
        System.out.println();
        opponentBoard.boardToText(true).forEach(System.out::println);
    }

    public void myTurn() {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        performAttack(new Coordinate(x, y), opponentBoard);
    }

    public void opponentTurn() {
        Coordinate coordinate = myBoard.randomCoordinate();
        System.out.println("Computer attacks " + coordinate);
        performAttack(coordinate, myBoard);
    }

    private void performAttack(Coordinate coordinate, Board board) {
        boolean hit = board.attackCoordinate(coordinate);
        if (hit) {
            System.out.println("BOOM!!!");
        } else {
            System.out.println("Miss");
        }
    }
}
