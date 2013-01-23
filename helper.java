import java.util.ArrayList;
import java.util.Arrays;

public class helper {
    private static final int NUM_FOOSPLAYERS = 26;
    private static boolean v = false;

    public static int getFatigueOfRow(int[] players, int[] fatigues) {
        int totalFatigue = 0;
        for (int player : players) {
            totalFatigue += fatigues[player];
        }

        return totalFatigue;
    }

    public static int getLeastFatiguedPlayer(int[] players, int[] fatigue) {
        if (v) {
            System.out.println("getLeastFatiguedPlayer called");
            System.out.println("players: "+ Arrays.toString(players));
            System.out.println("faitgue: "+Arrays.toString(fatigue));
        }

        int leastFatiguedPlayer = -1;
        int minFatigue = Integer.MAX_VALUE;

        for (int player : players) {
            if (fatigue[player] < minFatigue) {
                leastFatiguedPlayer = player;
                minFatigue = fatigue[player];
            }
        }

        return leastFatiguedPlayer;
    }

    public static int getMostFatiguedPlayer(int[] fatigue) {

        int maxFatigue = -1;
        int mostFatiguedPlayer = -1;

        for (int i = 0; i < fatigue.length; i++) {
            if (fatigue[i] > maxFatigue) {
                mostFatiguedPlayer = i;
                maxFatigue = fatigue[i];
            }
        }

        return mostFatiguedPlayer;
    }

    public static int[] getPlayersOnRow(int[] roster, int row) {
        ArrayList<Integer> players = new ArrayList<Integer>();

        for (int i = 0; i < NUM_FOOSPLAYERS; i++) {
            if (roster[i] == row) {
                players.add(i);
            }
        }

        int i = 0;
        int[] playersArr = new int[players.size()];

        for (int player : players) {
            playersArr[i++] = player;
        }

        return playersArr;
    }

    public static int[] getTeamRoster(int[] gameState) {
        int[] roster = new int[NUM_FOOSPLAYERS];
        System.arraycopy(gameState, 4, roster, 0, NUM_FOOSPLAYERS);

        return roster;
    }

    public static int[] getOppRoster(int[] gameState) {
        int[] oppRoster = new int[NUM_FOOSPLAYERS];
        System.arraycopy(gameState, 56, oppRoster, 0, NUM_FOOSPLAYERS);

        return oppRoster;
    }

    public static int[] getTeamFatigue(int[] gameState) {
        int[] teamFatigue = new int[NUM_FOOSPLAYERS];
        System.arraycopy(gameState, 30, teamFatigue, 0, NUM_FOOSPLAYERS);

        return teamFatigue;
    }

    public static int[] getOppFatigue(int[] gameState) {
        int[] oppFatigue = new int[NUM_FOOSPLAYERS];
        System.arraycopy(gameState, 82, oppFatigue, 0, NUM_FOOSPLAYERS);

        return oppFatigue;
    }

    public static int[] rowNumToRoster(int[] input) {
        int[] output = new int[26];
        // input.length should be 9 given 9 rows on the board
        if (input.length != 9) {
            return null;
        } else {
            int counter = 0;
            for(int i=0; i < 9; i++) {
                for(int j=0;j<input[i];j++) {
                    // hard coding the offset
                    output[counter] = i-4;
                    counter += 1;
                }
            }
            if (v) {
                System.out.println("counter should be 22 and is: "+counter);
            }
            for (int j=0;j<4;j++) {
                output[22+j] = 100;
            }
            return output;
        }
    }

    /**
     * basic logging to help analyse the results better, potentially
     *
     * @param gameState
    public static void logMoves(int[] gameState, String fileName) {
        try {

            double time = System.currentTimeMillis();
            FileWriter fstream = new FileWriter(fileName+".txt");
            BufferedWriter out = new BufferedWriter(fstream);

            out.write(Arrays.toString(gameState));
            //Close the output stream
            out.close();

        } catch (Exception e) {
            System.out.println("caught exception before it killed us");
        }


    }
     */
}
