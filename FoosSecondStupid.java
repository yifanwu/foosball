import java.util.Arrays;

public class FoosSecondStupid {

    /**
     * Mona & Yifan's awesome strategy of following the ball
     * and testing different strategies
     */

    public static int NUM_FOOSPLAYERS = 26;
    public static int NUM_FIELDED = 22;
    public static int ITER_PER_QUARTER = 200;
    public static int teamScore = 0;
    public static int myType = 0; //default is even
    private static boolean v = false;

    static final int[] silly_init = FoosStupid.silly_init;

//    static final int[] silly_init = {0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
//            0 , -4, -4, -4, -4, -4, -4, -4, -4, -4, -4,
//            100, 100, 100, 100};

    /*
     * keep track of the games played and choose based on their preportions
     * type result is even, offensive and defensive
     * initalized as 1:1:1 for math convenience
    */
    public static int[] GAME_TYPE_RESULT = new int[]{1, 1, 1};


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FoosBasic GAMEID"
                    + "\n\tGAMEID = 0    creates a new game"
                    + "\n\tGAMEID = WXYZ connect to a specific game");
            return;
        }

        // Connect to a FoosGame with id from the command line
        FoosGame game = new FoosGame(args[0]);

// Initial roster: select the better ones with high probability
// TODO: might be more aggressive about this
        myType = Strategies.chooseInitType(GAME_TYPE_RESULT);
        if (v) {
            System.out.println("the type is:" + myType);
            System.out.println("the row assignment is:" + Arrays.toString(Strategies.INIT_TYPES[myType]));

        }
        // int[] roster = Strategies.rowNumToRoster(Strategies.INIT_TYPES[myType]);
        int[] roster = silly_init;


        if (v) {
            System.out.println("my roaster: " + Arrays.toString(roster));
        }

        System.out.println("))))))))))))))))))))))");
        int[] game_state;
        while (true) {
            // Send the roster and get the game state
            game_state = game.make_move(roster);
            if (game_state[2] == 4 * ITER_PER_QUARTER)
                break;

// keep track of performance
            if (game_state[0] > teamScore) {
                GAME_TYPE_RESULT[myType] += 1;
            }

            // Use the game state to determine the next move
            if (game_state[2] % ITER_PER_QUARTER == 0)
                roster = FoosStupid.silly_init;
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
        return Strategies.oneGuyMoving(game_state);
    }
}


