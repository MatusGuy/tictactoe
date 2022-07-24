package classes;

public class Tile {
    public char sym;
    public Player owner;

    public Tile(final char s) {
        sym = s;
    }

    public Tile(final Player o) {
        this(o.sym);
        owner = o;
    }
}
