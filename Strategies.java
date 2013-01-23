import java.lang.Exception;
import java.util.ArrayList;
import java.util.Arrays;

public class Strategies {

    private static final int NUM_FOOSPLAYERS = 26;
    private static boolean v = false;
    public static enum GAME_TYPE {
        EVEN,OFFENSIVE,DEFENSIVE
    }

    /**
     * the rows are indexed per below
     * -4   -3  -2  -1  0   1   2   3   4
     * made two dementional for easier access
     */
    static final int[][] INIT_TYPES = {{3,1,2,2,3,3,2,3,3}, {2,1,1,1,4,3,3,3,4}, {4,3,3,1,4,3,1,1,2}};

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

        for (int j = 0; j < GAME_TYPE_RESULT.length; j++) {
            resultSum += GAME_TYPE_RESULT[j];
            if (randNum < resultSum/total) {
                return j;
            }
            // buggy
            return GAME_TYPE_RESULT.length-1;
        }
        return -1;
    }

    /**
     * This takes in the original layout and then swaps the tired player off
     * @param init
     * @return
     */
    public static int[] updateInitRosterForTired(int[] init, int[] gameState) {
        int mostFatigued = Helper.getMostFatiguedPlayer(Helper.getTeamFatigue(gameState));
        int positionOfFatigued = init[mostFatigued];
        init[mostFatigued] = 100;
        // find the first that's 100 and replace
        for(int i = 0; i<init.length; i++) {
            if (init[i] == 100) {
                init[i] = positionOfFatigued;
            }
        }

        return init;
    }

    /**
     * This assumes that the last row is the strongest
     * @param init
     * @return
     */
    public static int[] updateInitRosterForLastRow(int[] init) {
        int[] updated = new int[26];
        return updated;
    }

    // BETTER ONE EVEN THOUGH DOESN'T MAKE SENSE
    // moves player from more likely row to ball row
    public static int[] movePlayerTowardBall(int[] gameState) {
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
    public static int[] moveBasedOnFatigue(int[] gameState) {
        int[] roster = Helper.getTeamRoster(gameState);
        int[] oppRoster = Helper.getOppRoster(gameState);
        int[] myTeamFatigues = Helper.getTeamFatigue(gameState);
        int[] oppTeamFatigues = Helper.getTeamFatigue(gameState);

        int ballRow = gameState[3];

        int[] playersOnBallRow = Helper.getPlayersOnRow(roster, ballRow);
        int playersFatigue = Helper.getFatigueOfRow(playersOnBallRow, myTeamFatigues);

        int[] oppsOnBallRow = Helper.getPlayersOnRow(oppRoster, ballRow);
        int oppFatigue = Helper.getFatigueOfRow(oppsOnBallRow, oppTeamFatigues);

        int playerToMove = -1;

        if (oppFatigue > playersFatigue && ballRow - 1 > -5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow - 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, oppTeamFatigues);
        }
        else if (ballRow + 1 < 5) {
            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow + 1);
            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, oppTeamFatigues);
        }

        if (playerToMove != -1) {
            roster[playerToMove] = ballRow;
        }

        return roster;
    }


//
//    /**
//     * If the fatigue of the players that we have on the ball line is small enough
//     * that we shouldn't have to move another player onto the line, find another
//     * place to beef up players starting with the two rows on either side of the
//     * ball line and moving outwards
//     * @param gameState
//     * @return
//     */
//    public static int[] superAwesome(int[] gameState) {
//        int[] roster = Helper.getTeamRoster(gameState);
//        int[] oppRoster = Helper.getOppRoster(gameState);
//        int ballRow = gameState[3];
//
//        int[] playersOnBallRow = Helper.getPlayersOnRow(roster, ballRow);
//        int playersFatigue = Helper.getFatigueOfRow(playersOnBallRow);
//
//        int[] oppsOnBallRow = Helper.getPlayersOnRow(oppRoster, ballRow);
//        int oppFatigue = Helper.getFatigueOfRow(oppsOnBallRow);
//
//        int[] myTotalTeamFatigues = Helper.getTeamFatigue(gameState);
//        int playerToMove = -1;
//
//        // our players are unfatigued enough that we don't have to move someone
//        // to this row
//        if (playersFatigue < oppFatigue - 3) {
//            // look for another row
//        }
//        else {
//
//        }
//
//        if (oppFatigue > playersFatigue && ballRow - 1 > -5) {
//            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow - 1);
//            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTotalTeamFatigues);
//        }
//        else if (ballRow + 1 < 5) {
//            int[] playersToMove = Helper.getPlayersOnRow(roster, ballRow + 1);
//            playerToMove = Helper.getLeastFatiguedPlayer(playersToMove, myTotalTeamFatigues);
//        }
//
//        if (playerToMove != -1) {
//            roster[playerToMove] = ballRow;
//        }
//
//        return roster;
//    }
//
//    public static float[] probMovingFromRow(int row, int[] teamRoster, int[] oppRoster) {
//        float[] probs = new float[9];
//
//        for (int i = 0; i < Math.min(-4 - row, 4 - row); i++) {
//            int[] playersOnRow = Helper.getPlayersOnRow(teamRoster, row);
//            int[] oppsOnRow = Helper.getPlayersOnRow(oppRoster, row);
//
//            int myFatigue = Helper.getFatigueOfRow()
//
//        }
//
//    }

}

