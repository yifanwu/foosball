import java.util.ArrayList;

public class Strategies {

    private static final int NUM_FOOSPLAYERS = 26;

    /*
 * -4   -3  -2  -1  0   1   2   3   4
 * 3    1   2   2   3   3   2   3   3
 */

public static int[] GAME_TYPE_RESULT = [0,0,0];

public static enum GAME_TYPE {
    EVEN,OFFENSIVE,DEFENSIVE
}

static final int[] INIT_EVEN = [3,1,2,2,3,3,2,3,3];
static final int[] INIT_OFFENSIVE = [2,1,1,1,4,3,3,3,4];
static final int[] INIT_DEFENSIVE = [4,3,3,1,4,3,1,1,2];

static int[] rowNumToRoster(int[] input) {
    int[] output = int[22];

    // input.length = 9
    if (input.length != 9) {
        return null;
    } else {
        for(int i=0; i < 9; i++) {
            for(int j=0;j<input[i];j++) {
                // hard coding the offset
                output[i] = i-4;
            }
        }
    }
}

static GAME_TYPE chooseGameType() {
    
}


    public static int[] movePlayerTowardBall(int[] gameState) {
        
        int[] roster = getTeamRoster(gameState);
        int[] oppRoster = getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = getTeamFatigue(gameState);
        int playerToMove = 0;

        if (playersOnBallRow.length > oppsOnBallRow.length && ballRow - 1 >= -5) {
            int[] playersToMove = getPlayersOnRow(roster, ballRow - 1);
            playerToMove = getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }
        else if (ballRow + 1 <= 5) {
            int[] playersToMove = getPlayersOnRow(roster, ballRow + 1);
            playerToMove = getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }

        roster[playerToMove] = ballRow;
        return roster;
    }

    public static int getLeastFatiguedPlayer(int[] players, int[] fatigue) {
        int leastFatiguedPlayer = players[0];
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
        for (int i = 0; i < NUM_FOOSPLAYERS; i++) {
            roster[i] = gameState[i+4];
        }

        return roster;
    }

    public static int[] getOppRoster(int[] gameState) {
        int[] oppRoster = new int[NUM_FOOSPLAYERS];

        for (int i = 0; i < NUM_FOOSPLAYERS; i++) {
            oppRoster[i] = gameState[i+56];
        }

        return oppRoster;
    }

    public static int[] getTeamFatigue(int[] gameState) {
        int[] teamFatigue = new int[NUM_FOOSPLAYERS];

        for (int i = 0; i < NUM_FOOSPLAYERS; i++) {
            teamFatigue[i] = gameState[i+30];
        }

        return teamFatigue;
    }


    public static int[] getOppFatigue(int[] gameState) {
        int[] oppFatigue = new int[NUM_FOOSPLAYERS];

        for (int i = 0; i < NUM_FOOSPLAYERS; i++) {
            oppFatigue[i] = gameState[i+82];
        }

        return oppFatigue;
    }
}
