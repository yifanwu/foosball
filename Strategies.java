
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
        
        int[] roster = new int[NUM_FOOSPLAYERS];
        int[] oppRoster = new int[NUM_FOOSPLAYERS];

        for (int i = 0; i < NUM_FOOSPLAYERS; i++) {
            roster[i] = gameState[i+4];
        }

        for (int i = 0; i < NUM_FOOSPLAYERS; i++) {

        }

        int ballRow = gameState[3];
        return roster;

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
