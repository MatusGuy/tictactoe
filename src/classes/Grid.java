package classes;

import java.util.ArrayList;

public class Grid{
    public final int size;

    public Tile[][] tiles;
    public ArrayList<Player> playing = new ArrayList<Player>();
    public Move lastMove = null;

    final private static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

    public Grid(final int s) {
        size = s;

        tiles = new Tile[size][size];
    }

    public Tile doesTileExist(final int row, final int col) {
        try {
            return tiles[row][col];
        } catch (final NullPointerException ex) {
            return null;
        }
    }

    private String multiplyStr(final String s, final int n) {
        return new String(new char[n]).replace("\0", String.valueOf(s));
    }

    public void draw() {
        // show column ids
        StringBuilder colIdOut = new StringBuilder("  ");
        for (int ri = 0; ri<size; ri++) {
            colIdOut.append(alphabet[ri]).append(' ');
        }
        System.out.println(colIdOut);
        System.out.println(' ');

        for (int ri = 0; ri<size; ri++) {

            StringBuilder out = new StringBuilder((ri+1)+" ");
            for (int ci = 0; ci<size; ci++) {
                final Tile tile = doesTileExist(ri, ci);

                final char tileSym = tile!=null ? tile.sym : ' ';
                final char sep = ci+1<size ? '|' : ' ';
                out.append(tileSym).append(sep);
            }

            System.out.println(out);
            if (ri+1<size) {
                System.out.println("  " + multiplyStr("-|", size-1) + "-");
            }
        }
        System.out.println(' ');
    }

    public boolean horLineCheck(Player owner) {
        for (final Tile[] row : tiles) {

            // row owner equal
            int oe = 0;

            for (final Tile tile : row) if (tile!=null && tile.owner.equals(owner)) oe++;

            if (oe == size) return true;
        }
        
        return false;
    }

    public boolean verLineCheck(Player owner) {
        for (int col = 0; col<size; col++) {

            int oe = 0;

            for (int ri = 0; ri<tiles.length; ri++) {
                final Tile tile = doesTileExist(ri, col);
                if (tile!=null && tile.owner.equals(owner)) oe++;
            }

            if (oe == size) return true;
        }
        
        return false;
    }

    public boolean diagLineCheck(Player owner) {
        int oe = 0;
        for (int i = 0; i<size; i++) {
            final Tile tile = doesTileExist(i,i);
            if (tile!=null && tile.owner.equals(owner)) oe++;
        }
        return oe==size;
    }

    public boolean leftDiagLineCheck(Player owner) {
        int oe = 0;
        for (int i = size-1; i>=0; i--) {
            final Tile tile = doesTileExist(size-i-1,i);
            if (tile!=null && tile.owner.equals(owner)) oe++;
        }
        return oe==size;
    }

    public boolean isFull() {
        int count = 0;
        for (final Tile[] row : tiles) {
            for (final Tile tile : row) {
                if (tile != null) count++;
            }
        }

        return count == size*size;
    }

    protected Move winCheck(Move move) {
        for (final Player player : playing) {
            if (
                horLineCheck(player) ||
                verLineCheck(player) ||
                diagLineCheck(player) ||
                leftDiagLineCheck(player)
            ) {
                move.player = player;
                move.win = true;
                return move;
            }
        }

        if (isFull()) {
            move.tie = true;
            return move;
        }

        return move;
    }

    public Move set(final Coordinates coords, final Tile tile) {
        final boolean cancel = doesTileExist(coords.row, coords.col)!=null;

        Move move = new Move();
        move.coords = coords;
        move.tile = tile;
        if (!cancel) {
            tiles[coords.row][coords.col] = tile;
            winCheck(move);
            lastMove = move;
        } else {
            move.cancelled = true;
        }

        return move;
    }
}
