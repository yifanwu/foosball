import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;

class foosFoo {
    public static int NUM_FOOSPLAYERS = 26;
    public static int NUM_FIELDED = 22;
    public static int ITER_PER_QUARTER = 200;
    public static int myType = -1;
    public static String fileName = "yifan_itr_1.txt";
    public static int teamScore = 0;

    /**
     * keep track of the games played and choose based on their preportions
     * type result is even, offensive and defensive
     * initalized as 1:1:1 for math convenience
     */
    public static int[] GAME_TYPE_RESULT = new int[]{1,1,1};


    public static void main(String[] args) {

        int[] game_state;

        if (args.length != 1) {
            System.out.println("Usage: java foosteam GAMEID"
                    + "\n\tGAMEID = 0    creates a new game"
                    + "\n\tGAMEID = WXYZ connect to a specific game");
            return;
        }

        double time = System.currentTimeMillis();

        BufferedWriter outBuffer = null;

        try {
            FileWriter fstream = new FileWriter(fileName);
            outBuffer = new BufferedWriter(fstream);

        } catch (Exception e) {
            System.out.println("canot write to file: "+fileName);
        }


        // Connect to a FoosGame with id from the command line
        FoosGame game = new FoosGame(args[0]);

        myType = Strategies.chooseInitType(GAME_TYPE_RESULT);

        try {
            outBuffer.write("the play type is: "+myType);
        } catch (Exception e) {
            System.out.println("cannot write myType: "+myType);
        }

        int[] roster = helper.rowNumToRoster(Strategies.INIT_TYPES[myType]);


        while (true) {
            // Send the roster and get the game state
            game_state = game.make_move(roster);
            if (game_state[2] == 4 * ITER_PER_QUARTER)
                break;
                // every quater choose a new roaster
            else if ((game_state[2]%ITER_PER_QUARTER) == 0) {
                // update myType!
                myType = Strategies.chooseInitType(GAME_TYPE_RESULT);
                // update roaster --- no longer contrained by moving by one!
                int[] init = helper.rowNumToRoster(Strategies.INIT_TYPES[myType]);
                // update off the tired one
                roster = Strategies.updateInitRosterForTired(init, game_state);

            } else {

                // Use the game state to determine the next move
                roster = new_move(game_state);

            }

            // keep track of performance
            if (game_state[0] > teamScore) {
                GAME_TYPE_RESULT[myType] += 1;
                teamScore = game_state[0];
                try {
                    outBuffer.write("we won via: \n"+Arrays.toString(game_state));
                } catch (Exception e) {
                    System.out.println("cannot write game_state");
                }

            }
        }

        System.out.println("Final Score: " + game_state[0] + " - " + game_state[1]);

        if (game_state[0] > game_state[1])
            System.out.println("WIN!");
        if (game_state[0] == game_state[1])
            System.out.println("Tie Game");
        try {
            outBuffer.close();
        } catch (Exception e) {
            System.out.println("caught exception before it killed us");
        }
    }


    public static int[] new_move(int[] game_state) {

        return Strategies.moveBasedOnFatigue(game_state);
    }


}
