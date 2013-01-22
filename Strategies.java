import java.lang.Exception;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.io.*;

public class Strategies {

    private static final int NUM_FOOSPLAYERS = 26;
    private static boolean v = true;
    public static enum GAME_TYPE {
        EVEN,OFFENSIVE,DEFENSIVE
    }

    // Create file



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

    // moves player from less likely row to ball row
    public static int[] movePlayerTowardBall(int[] gameState) {
        int[] roster = Helper.getTeamRoster(gameState);
        int[] oppRoster = Helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = Helper.getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = Helper.getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = Helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        if (playersOnBallRow.length > oppsOnBallRow.length && ballRow - 1 > -5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }

    // BETTER ONE EVEN THOUGH DOESN'T MAKE SENSE
    // moves player from more likely row to ball row
    public static int[] movePlayerTowardBall2(int[] gameState) {
        int[] roster = Helper.getTeamRoster(gameState);
        int[] oppRoster = Helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = Helper.getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = Helper.getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = Helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        if (playersOnBallRow.length < oppsOnBallRow.length && ballRow - 1 > -5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }

    // does not work as well as not trying to keep at least one player on each row
    public static int[] keepAtLeastOnePlayerOnEachRow(int[] gameState) {
        int[] roster = Helper.getTeamRoster(gameState);
        int[] oppRoster = Helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = Helper.getPlayersOnRow(roster, ballRow);
        int[] oppsOnBallRow = Helper.getPlayersOnRow(oppRoster, ballRow);
        int[] myTeamFatigues = Helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        int[] defenseSidePlayers = Helper.getPlayersOnRow(roster, ballRow - 1);
        int defenseSideLeastFatigued = Helper.getLeastFatiguedPlayer(defenseSidePlayers, myTeamFatigues);
        int[] offenseSidePlayers = Helper.getPlayersOnRow(roster, ballRow + 1);
        int offenseSideLeastFatigued = Helper.getLeastFatiguedPlayer(offenseSidePlayers, myTeamFatigues);


        if (playersOnBallRow.length < oppsOnBallRow.length && ballRow - 1 > -5) {
            playerToMove = (defenseSidePlayers.length > 1) ? defenseSideLeastFatigued : -1;
        }
        else if (ballRow + 1 < 5) {
            playerToMove = (offenseSidePlayers.length > 1) ? offenseSideLeastFatigued : -1;
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }

    // moves player from less likely row (based on fatigue) to ball row
    // performs pretty equal to movePlayerTowardBall2
    public static int[] moveBasedOnFatigue2(int[] gameState) {
        int[] roster = Helper.getTeamRoster(gameState);
        int[] oppRoster = Helper.getOppRoster(gameState);
        int ballRow = gameState[3];

        int[] playersOnBallRow = Helper.getPlayersOnRow(roster, ballRow);
        int playersFatigue = Helper.getTotalFatigue(playersOnBallRow);

        int[] oppsOnBallRow = Helper.getPlayersOnRow(oppRoster, ballRow);
        int oppFatigue = Helper.getTotalFatigue(oppsOnBallRow);

        int[] myTotalTeamFatigues = Helper.getTeamFatigue(gameState);
        int playerToMove = -1;

        if (oppFatigue > playersFatigue && ballRow - 1 > -5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTotalTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTotalTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }

    public static void logMoves(int[] gameState) {
        try {

            double time = System.currentTimeMillis();
            FileWriter fstream = new FileWriter(time+".txt");
            BufferedWriter out = new BufferedWriter(fstream);

            out.write(Arrays.toString(gameState));
            //Close the output stream
            out.close();

        } catch (Exception e) {
            System.out.println("caught exception before it killed us");
        }


    }

}

