import java.lang.ArrayStoreException;
import java.lang.System;
import java.util.Arrays;

class FoosIdiot {

    public static int NUM_FOOSPLAYERS = 26;
    public static int NUM_FIELDED = 22;
    public static int ITER_PER_QUARTER = 200;


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FoosBasic GAMEID"
                    + "\n\tGAMEID = 0    creates a new game"
                    + "\n\tGAMEID = WXYZ connect to a specific game");
            return;
        }

        // Connect to a FoosGame with id from the command line
        FoosGame game = new FoosGame(args[0]);

        // int[] roster = Strategies.rowNumToRoster(Strategies.INIT_TYPES[myType]);
        int[] roster = roster = new int[]{100, 100, 100, 100,
                -4, -4, -4, -4, -4, -4, -4, -3, -3, -2, -1,
                0, 1, 2, 3, 3, 4, 4, 4, 4, 4, 4};

        int[] game_state;
        while (true) {
            // Send the roster and get the game state
            game_state = game.make_move(roster);
            if (game_state[2] == 4 * ITER_PER_QUARTER)
                break;

            // Use the game state to determine the next move
            if (game_state[2] == 200)
                roster = new int[]{0, 100, 100, 100,
                        -4, -4, -4, -4, -4, -4, -4, -3, -3, -2, -1,
                        100, 1, 2, 3, 3, 4, 4, 4, 4, 4, 4};
            else if (game_state[2] == 400) {
                roster = new int[]{0, -1, 100, 100,
                        -4, -4, -4, -4, -4, -4, -4, -3, -3, -2, 100,
                        100, 1, 2, 3, 3, 4, 4, 4, 4, 4, 4};
            } else if (game_state[2] == 600) {
                roster = new int[]{0, -1, 1, 100,
                        -4, -4, -4, -4, -4, -4, -4, -3, -3, -2, 100,
                        100, 100, 2, 3, 3, 4, 4, 4, 4, 4, 4};
            }

            else
                roster = new_move(game_state);
        }


        System.out.println("Final Score: " + game_state[0] + " - " + game_state[1]);
        if (game_state[0] > game_state[1])
            System.out.println("WIN!");
        if (game_state[0] == game_state[1])
            System.out.println("Tie Game");

    }

    public static int[] new_move(int[] game_state) {
        // Trivial strategy, null move
                                     //
        System.out.println("Game State is: "+Arrays.toString(game_state));
        return Strategies.readOppPos(game_state);
    }
}

