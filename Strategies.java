
public class Strategies {

    private static final int NUM_FOOSPLAYERS = 26;

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
