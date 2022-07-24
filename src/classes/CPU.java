package classes;

import java.util.Random;

public class CPU extends Player {
    private static Random randomizer = new Random();

    public CPU(final char sym, final Grid g) {
        super("CPU", sym, g);
    }

    private Move setForChecks(final Coordinates coords) {
        if (grid.doesTileExist(coords.row, coords.col)==null) {
            return set(coords);
        }
        return null;
    }

    public Move rowCheck(final Player owner) {
        final int gridsize = grid.size;
        for (int ri = 0; ri<gridsize; ri++) {
            final Tile[] row = grid.tiles[ri];

            // row owner equal
            int oe = 0;
            int ei = 0;
            for (int ci = 0; ci<gridsize; ci++) {
                final Tile tile = row[ci];

                if (tile==null) {
                    ei = ci;
                } else if (tile.owner.equals(owner)) {
                    oe++;
                }
            };

            if (oe == gridsize-1) {
                return setForChecks(new Coordinates(ei, ri));
            };
        }
        
        return null;
    }

    public Move colCheck(final Player owner) {
        final int gridsize = grid.size;
        for (int col = 0; col<gridsize; col++) {

            int oe = 0;
            int ei = 0;

            for (int ri = 0; ri<gridsize; ri++) {
                final Tile tile = grid.doesTileExist(ri, col);

                if (tile==null) {
                    ei = ri;
                } else if (tile.owner.equals(owner)) {
                    oe++;
                }
            }
            if (oe == gridsize-1) {
                return setForChecks(new Coordinates(col, ei));
            }
        }
        
        return null;
    }

    public Move tlDiagCheck(final Player owner) {
        final int gridsize = grid.size;
        int oe = 0;
        int ei = 0;
        for (int i = 0; i<gridsize; i++) {
            final Tile tile = grid.doesTileExist(i,i);
            if (tile==null) {
                ei = i;
            } else if (tile.owner.equals(owner)) {
                oe++;
            }
        }
        if (oe == gridsize-1) {
            return setForChecks(new Coordinates(ei, ei));
        };
        return null;
    }

    public Move blDiagCheck(final Player owner) {
        final int gridsize = grid.size;
        int oe = 0;
        int ei = 0;
        for (int i = gridsize-1; i>=0; i--) {
            final Tile tile = grid.doesTileExist(i,i);
            if (tile==null) {
                ei = i;
            } else if (tile.owner.equals(owner)) {
                oe++;
            }
        }
        if (oe == gridsize-1) {
            return setForChecks(new Coordinates(ei, ei));
        };
        return null;
    }

    public Move trDiagCheck(final Player owner) {
        final int gridsize = grid.size;
        int oe = 0;
        int ei = 0;
        for (int i = gridsize-1; i>=0; i--) {
            final Tile tile = grid.doesTileExist(gridsize-i-1,i);
            if (tile==null) {
                ei = i;
            } else if (tile.owner.equals(owner)) {
                oe++;
            }
        }
        if (oe == gridsize-1) {
            return setForChecks(new Coordinates(ei, ei));
        };
        return null;
    }

    public Move brDiagCheck(final Player owner) {
        final int gridsize = grid.size;
        int oe = 0;
        int ei = 0;
        for (int i = 0; i<0; i++) {
            final Tile tile = grid.doesTileExist(gridsize-i-1,i);
            if (tile==null) {
                ei = i;
            } else if (tile.owner.equals(owner)) {
                oe++;
            }
        }
        if (oe == gridsize-1) {
            return setForChecks(new Coordinates(ei, ei));
        };
        return null;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((randomizer.nextDouble() * (max - min)) + min);
    }

    private Move possibleLossChecks(Player player) {
        final Move rowcheck = rowCheck(player);
        if (rowcheck!=null) return rowcheck;

        final Move colcheck = colCheck(player);
        if (colcheck!=null) return colcheck;

        final Move tld = tlDiagCheck(player);
        if (tld!=null) return tld;

        final Move trd = trDiagCheck(player);
        if (trd!=null) return trd;

        final Move bld = blDiagCheck(player);
        if (bld!=null) return bld;

        final Move brd = brDiagCheck(player);
        if (brd!=null) return brd;
    
        return null;

    }

    private Move possibleLossChecksToAll() {
        Move checks = possibleLossChecks(this);
        if (checks!=null) return checks;

        for (final Player player : grid.playing) {
            if (!player.equals(this)) {
                checks = possibleLossChecks(player);
                if (checks!=null) return checks;
            }
        }

        return null;
    }

    @Override
    public Move input() {
        
        final Move losschecks = possibleLossChecksToAll();
        if (losschecks!=null) return losschecks;

        int gridsize = grid.size-1;

        Move resp = null;
        while (resp == null || resp.cancelled) {
            int x = getRandomNumber(0, gridsize);
            int y = getRandomNumber(0, gridsize);
            resp = set(new Coordinates(x,y));
        }
        return resp;
    }
}