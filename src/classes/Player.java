package classes;

import java.util.Scanner;

public class Player {
    private static Scanner scanner = new Scanner(System.in);

    final public String name;
    final public char sym;
    final protected Grid grid;

    public Player(final String n, final char s, Grid g) {
        name = n;
        sym = s;

        grid = g;
        grid.playing.add(this);
    }
    
    protected Coordinates askForCoords() {
        System.out.print(name + ", place a tile: ");
        String coors = scanner.nextLine().toUpperCase();

        if (
            coors.length() != 2 ||
            !Character.isLetter(coors.charAt(0)) ||
            !Character.isDigit(coors.charAt(1))
        ) {
            System.out.println("Send valid coordinates, for example A2");
            return null;
        }

        System.out.println(' ');

        return new Coordinates(coors);
    }

    protected Move set(Coordinates coords) {
        Move move = grid.set(coords, new Tile(this));
        move.player = this;
        return move;
    }

    public Move input() {
        Coordinates coors = null;
        while (coors == null) {
            coors = askForCoords();
        }

        return set(coors);
    }
}
