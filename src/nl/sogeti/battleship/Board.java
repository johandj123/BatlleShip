package nl.sogeti.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Board {
    public static int SIZE = 10;

    public static List<Integer> BOAT_SIZES = List.of(5, 4, 3, 3, 2);

    private boolean occupied[][] = new boolean[SIZE][SIZE];

    private boolean guessed[][] = new boolean[SIZE][SIZE];

    public Board() {
        placeBoatsAtRandom();
    }

    private void placeBoatsAtRandom() {
        Random random = new Random();
        for (int size : BOAT_SIZES) {
            placeBoatAtRandom(random, size);
        }
    }

    private void placeBoatAtRandom(Random random, int size) {
        boolean placed;
        do {
            boolean horizontal = random.nextBoolean();
            int x;
            int y;
            if (horizontal) {
                x = random.nextInt(SIZE - size + 1);
                y = random.nextInt(SIZE);
                placed = tryPlaceBoatHorizontal(size, x, y);
            } else {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE - size + 1);
                placed = tryPlaceBoatVertical(size, x, y);
            }
        } while (!placed);
    }

    private boolean tryPlaceBoatHorizontal(int size, int x, int y) {
        boolean result = IntStream.range(x, x + size).noneMatch(nx -> occupied[y][nx]);
        if (result) {
            IntStream.range(x, x + size).forEach(nx -> occupied[y][nx] = true);
        }
        return result;
    }

    private boolean tryPlaceBoatVertical(int size, int x, int y) {
        boolean result = IntStream.range(y, y + size).noneMatch(ny -> occupied[ny][x]);
        if (result) {
            IntStream.range(y, y + size).forEach(ny -> occupied[ny][x] = true);
        }
        return result;
    }

    public boolean isGameLost() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (occupied[y][x] && !guessed[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean attackCoordinate(Coordinate coordinate)
    {
        guessed[coordinate.getY()][coordinate.getX()] = true;
        return occupied[coordinate.getY()][coordinate.getX()];
    }

    public List<String> boardToText(boolean opponent)
    {
        List<String> result = new ArrayList<>();
        for (int y = 0; y < SIZE; y++) {
            StringBuilder sb = new StringBuilder();
            sb.append(y);
            for (int x = 0; x < SIZE; x++) {
                sb.append('|');
                char c = opponent ? '?' : ' ';
                boolean fieldOccupied = occupied[y][x];
                boolean fieldGuessed = guessed[y][x];
                if (fieldOccupied) {
                    if (fieldGuessed) {
                        c = '*';
                    } else if (!opponent) {
                        c = 'X';
                    }
                } else if (fieldGuessed) {
                    c = opponent ? ' ' : '.';
                }
                sb.append(c);
            }
            sb.append('|');
            result.add(sb.toString());
        }

        StringBuilder sb = new StringBuilder();
        sb.append(' ');
        for (int x = 0; x < SIZE; x++) {
            sb.append('|');
            sb.append(x);
        }
        sb.append('|');
        result.add(sb.toString());

        return result;
    }

    public Coordinate randomCoordinate()
    {
        Random random = new Random();
        return new Coordinate(random.nextInt(SIZE), random.nextInt(SIZE));
    }
}
