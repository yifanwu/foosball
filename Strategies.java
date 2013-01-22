public class Strategies {

    private static final int NUM_FOOSPLAYERS = 26;
    private static boolean v = true;
    public static enum GAME_TYPE {
        EVEN,OFFENSIVE,DEFENSIVE
    }

    /* the rows are indexed per below
     * -4   -3  -2  -1  0   1   2   3   4
     */
    static final int[][] INIT_TYPES = {{3,1,2,2,3,3,2,3,3}, {2,1,1,1,4,3,3,3,4}, {4,3,3,1,4,3,1,1,2}};

    /*
    static final int[] INIT_EVEN = new int[]{3,1,2,2,3,3,2,3,3};
    static final int[] INIT_OFFENSIVE = new int[]{2,1,1,1,4,3,3,3,4};
    static final int[] INIT_DEFENSIVE = new int[]{4,3,3,1,4,3,1,1,2};
    */
    //static final int[][] intToType = new int[][]{INIT_EVEN,INIT_OFFENSIVE,INIT_DEFENSIVE};
    // to be filled in later as our init board
    //static int[] INIT;

    public static int[] rowNumToRoster(int[] input) {
        int[] output = new int[26];

        // input.length = 9
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

    public static int chooseInitType(int[] GAME_TYPE_RESULT) {
        double randNum = Math.random();
        if (v) {
            System.out.println("the random number is: "+randNum);
        }
        double total = 0.0;

        for (int x: GAME_TYPE_RESULT) {
            total += x;
        }

        int resultSum = 0;

        for (int j = 0; j<GAME_TYPE_RESULT.length; j++) {
            resultSum += GAME_TYPE_RESULT[j];
            if (randNum < resultSum/total) {
                return j;
            }
            // buggy
            return GAME_TYPE_RESULT.length-1;
        }
        return -1;
    }

    public static int[] movePlayerTowardBall(int[] gameState) {
        int[] roster = helper.getTeamRoster(gameState);
        int[] oppRoster = helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = helper.getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = helper.getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        if (playersOnBallRow.length > oppsOnBallRow.length && ballRow - 1 > -5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }


    public static int[] movePlayerTowardBall2(int[] gameState) {
        int[] roster = helper.getTeamRoster(gameState);
        int[] oppRoster = helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = helper.getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = helper.getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        if (playersOnBallRow.length < oppsOnBallRow.length && ballRow - 1 > -5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }
}

