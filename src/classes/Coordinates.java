package classes;

public class Coordinates {
    public int row;
    public int col;

    final private static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

    private static Integer findLetter(final char l) {
        for (Integer i = 0; i<alphabet.length; i++) {
            final char v = alphabet[i];
            if (v==l) {
                return i+1;
            }
        }
        return null;
    }

    public Coordinates(final int c, final int r) {
        col = c;
        row = r;
    }

    public Coordinates(final char c, final int r) {
        this(Coordinates.findLetter(Character.toUpperCase(c))-1, r-1);
    }

    public Coordinates(final String resp) {
        this(resp.charAt(0), resp.charAt(1) - '0');
    }
}