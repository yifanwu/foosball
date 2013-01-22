import java.util.ArrayList;
import java.util.Arrays;

public class Helper {
    private static final int NUM_FOOSPLAYERS = 26;
    private static boolean v = true;

    public static int getTotalFatigue(int[] playersFatigue) {
        int totalFatigue = 0;
        for (int fatigue : playersFatigue) {
            totalFatigue += fatigue;
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
}