import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import classes.*;

public class game {
    static final Scanner input = new Scanner(System.in);

    static final Grid grid = new Grid(3);
    static Player player;
    static Player opponent;
    static Player turn;

    static String ask(String msg) {
        System.out.print(msg+": ");
        return input.nextLine();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe! Press Ctrl-C to quit.");
        
        try {
            //player = new CPU('X',grid);
            player = new Player(ask("Enter Player 1's name"), 'X', grid);
            //opponent = new Player(ask("Enter Player 2's name"), 'O', grid);
            opponent = new CPU('O',grid);
            turn = player;

            while (true) {
                if (!gameLoop()) {
                    break;
                }
            }
        } catch (final NoSuchElementException ex) {
            System.out.printf(
                "\n\nGoodbye %s and %s!\n",
                player!=null ? player.name : "player",
                opponent!=null ? opponent.name : "opponent"
            );
        }
    }

    /**
     * @return should keep playing
     */
    static boolean gameLoop() {
        grid.draw();

        if (turn.getClass().getSimpleName() == "CPU") {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Move move = turn.input();
        if (move.win || move.tie) {
            grid.draw();
            if (move.tie) {
                System.out.println("That's a tie!");
            } else if (move.win) {
                System.out.println(turn.name+" wins!");
            }
            return false;
        }

        turn = turn.equals(player) ? opponent : player;

        return true;
    }
}
